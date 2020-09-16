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
    
    