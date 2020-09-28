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

##
1. 로그인 시 토큰 발급 및 세션 정보 담아둔다.
2. 토큰 넘어오면 토큰 체크 및 세션정보 조회.

## 임시
msa 에서 모든 데이터는 항상 일관성을 유지해야된다는 생각은 버리는게 좋다
모든 데이터는 일관성을 유지할수 없다는걸 기본으로 깔고 데이터의 중요도, 일관성이 깨졌을때의 리스크 등을 고려해서 일관성을 어느수준으로 관리할지, 일관성이 깨진것을 어떻게 탐지할지,
복구는 어느 수준으로 할지 등을 정해야함

일관성 유지방법
- 일단 강력한 일관성을 보장해야하는 데이터는 같은 서비스 안에 있어야된다는게 가장 중요,
위 규칙을 지켜서 서비스 설계를 하면 서비스간 데이터 일관성을 높은 수준으로 유지해야하는 경우는 생각보다 많이 없음
그럼에도 서비스를 쪼개서 얻는 이점이 크다면 사가패턴(정확히 똑같지 않음, 데이터 중요도에 따라 적절하게 커스터마이징함) 처럼 최종일관성을 맞추는 방향을 선호함.

### 설계
- 보안 정책
    - 로그인 시에만 접근
        - /api/member/*  ???
        - POST, PUT, DELETE /api/article/1 -> ??? 
        - POST, DELETE /api/article/1/like -> ????
    - 인증 없이도 접근

## References
- https://github.com/svlada/springboot-security-jwt