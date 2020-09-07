# Spring Boot Security

## 서버 기반 인증 vs 토큰 기반 인증
- 서버 (stateful)
    - 사용자들의 정보를 기억하기 위해 세션 유지 (세션 저장소)
    - 분산 시스템 설계 난이도
- 토큰 (stateless)
    - 인증한 사용자들에게 토큰 발급, 서버 요청 시 헤더에 토큰과 함께 전달 후 유효성 검사

## Testing
### 403 
- https://stackoverflow.com/questions/21749781/why-i-received-an-error-403-with-mockmvc-and-junit

### References
- https://spring.io/blog/2014/05/07/preview-spring-security-test-method-security
- https://velog.io/@minholee_93/series/Spring-Security