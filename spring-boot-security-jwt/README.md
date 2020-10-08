# Spring Security with JWT

## 개요
- 토큰을 이용한 시큐리티 인증 방식 구현
- Spring Security -> credential 기반 인증
    - principal: 아이디
    - credential: 비밀번호

## 주요 컴포넌트
- Authentication
    - 인증 성공 시 사용자의 principal, credential 정보를 Authentication 객체에 담는다.
- SecurityContext
    - Authentication 보관
- SecurityContextHolder
    - SecurityContext 보관

## 인증 처리
1. UsernamePasswordAuthenticationToken 생성
2. 토큰을 AuthenticationManager 에 전달
3. 인증 성공 시 Authentication 반환
4. Authentication -> SecurityContext -> SecurityContextHolder
```
@Override
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                        Authentication authResult) throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    chain.doFilter(request, response);
}

// 로그인한 사용자 정보 얻기
SecurityContextHolder.getContext().getAuthentication().getPrincipal();
```

## 기본 인증 처리 과정
> Authentication(인증) -> Authorization(인가)
1. AbstractAuthenticationProcessingFilter
2. UsernamePasswordAuthenticationFilter (구현체)
3. 요청한 사용자명과 비밀번호 획득 후 해당 정보를 담은 토큰 생성
4. AuthenticationManager 를 사용하여 크리덴셜(토큰) 검증
5. ProviderManager (구현체)
6. 모든 AuthenticationProvider 객체를 순환하여 검사
7. 인증 성공 시 인증된 사용자 및 권한 정보를 담은 Authentication 객체 반환

### 코드 흐름
1. AbstractAuthenticationProcessingFilter
    - 인증 요청에 대한 URL 감지 시 요청 가로챔
    - 기본적으로 http.formLogin() 사용 시 UsernamePasswordAuthenticationFilter 구현체 이용
        - UsernamePasswordAuthenticationToken 토큰에 인증 정보 담음
    - 인증 정보를 담은 객체를 인증 프로세스 객체(AuthenticationProvider) 에게 전달
        - AbstractAuthenticationProcessingFilter.attemptAuthentication -> AuthenticationProvider.authenticate
2. Filter 가 주입 받은 AuthenticationManager 의 authenticate(token) 호출
    - AuthenticationManager
        - 사용자명, 비밀번호 인증 작업 진행
        - AuthenticationManager 에 연결 된 provider 를 이용하여 인증 작업 진행
        ```
        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(jwtAuthenticationProvider);
        }
        ```
      
## 예외
> ExceptionTranslationFilter -> AuthenticationEntryPoint
- Authentication(인증), Authorization(인가) 과정에서의 예외 발생 시 ExceptionTranslationFilter
    - AuthenticationException 발생 시 AuthenticationEntryPoint 실행 하여 인증 유도
    - AccessDeniedException 발생 시 
        - 현재 Authentication 이 AnonymousUser 라면 AuthenticationEntryPoint 실행 후 인증 유도
        - 아니라면 AccessDeniedHandler 위임
- AuthenticationEntryPoint 
    - 인증 되지 않은 사용자가 보호된 리소스 접근 시
- AbstractAuthenticationProcessingFilter 
    - doFilter -> successfulAuthentication -> successHandler.onAuthenticationSuccess(request, response, authResult);
    - unsuccessfulAuthentication 
    

## 비동기 환경
### WebAsyncManagerIntegrationFilter
- 스프링 MVC 의 Async 기능을 사용할 때도 SecurityContext 를 공유하도록 도와주는 필터
- PreProcess: SecurityContext 설정
- Callable: 비록 다른 쓰레드지만 그 안에서는 동일한 SecurityContext 를 참조할 수 있다. 
- PostProcess: SecurityContext 정리(clean up)
- MVC 요청이 들어오는 쓰레드 작업을 완료하고 나서도 SecurityContextHolder 에서는 사용자 정보를 동일하게 얻을 수 있다.
    - WebAsyncManagerIntegrationFilter
```
@Controller
public class SampleController {

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        // http-nio-8080-exec 쓰레드
        SecurityLogger.log("MVC");

        return () -> {
            // task-1 쓰레드
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }
}
```

### SecurityContextCallableProcessingInterceptor
- WebAsyncManagerIntegrationFilter 는 SecurityContextCallableProcessingInterceptor 를 사용해서 SecurityContextHolder 에 SecurityContext 저장

### @Async 서비스에서 SecurityContextHolder 공유
- SecurityContextHolder 기본 전략은 ThreadLocal
- @Async 서비스에서 SecurityContextHolder 가 공유 되지 않는 문제 발생
- SecurityContextHolder 전략을 다음 코드와 같이 바꾸면 쓰레드 계층 사이에서도 SecurityContextHolder 정보가 공유된다
```
SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
```

### 설계

### 흐름
- 클라이언트가 서버로 로그인 한다. (ID, PW)
- 서버에서 사용자를 확인하고 Access Token 과 Refresh Token 을 함께 전송
- 클라이언트는 쿠키 또는 메모리에 토큰을 저장하고, Access Token 을 사용하여 서버 로그인
- 서버는 토큰을 검증하여 요청에 응답
- Access Token 만료 시 Unauthorized 응답
- 서버로 부터 Unauthorized 응답을 받으면 Refresh Token 으로 Access Token 을 재발급 한다.
- Refresh Token 만료 시 클라이언트에게 다시 로그인 할 수 있도록 응답해야 한다. 
    - 기존 Access Token 과 Refresh Token 을 비우고 다시 로그인 하게 해야 한다.
    - 헤더에 토큰 실어서 요청 시 Refresh 토큰 만료 시간을 계속 갱신해야하나...? -> 게시글 작성 중 Refresh 토큰 만료 되면 우쨔? 
- 로그아웃을 누르면 모든 토큰 제거

### 보안 정책
- 로그인 시에만 접근
    - /api/member/*  ???
    - POST, PUT, DELETE /api/article/1 -> ??? 
    - POST, DELETE /api/article/1/like -> ????
- 인증 없이도 접근
- 토큰은 쿠키에 저장한다. (local storage 는 xss 공격에 취약)
- 로그아웃
    - 쿠키 삭제 또는 블랙리스트 관리

## References
- https://github.com/svlada/springboot-security-jwt
- https://godekdls.github.io/Spring%20Security/contents/
- https://blog.ull.im/engineering/2019/02/07/jwt-strategy.html