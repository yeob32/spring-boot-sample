# Spring Boot Security

## 서버 기반 인증 vs 토큰 기반 인증
- 서버 (stateful)
    - 사용자들의 정보를 기억하기 위해 세션 유지 (세션 저장소)
    - 분산 시스템 설계 난이도
- 토큰 (stateless)
    - 인증한 사용자들에게 토큰 발급, 서버 요청 시 헤더에 토큰과 함께 전달 후 유효성 검사

## 기본 용어
- 접근 주체(Principal) : 보호된 대상에 접근하는 유저
- 인증(Authenticate) : 현재 유저가 누구인지 확인(ex. 로그인)
    - 애플리케이션의 작업을 수행할 수 있는 주체임을 증명
- 인가(Authorize) : 현재 유저가 어떤 서비스, 페이지에 접근할 수 있는 권한이 있는지 검사
- 권한 : 인증된 주체가 애플리케이션의 동작을 수행할 수 있도록 허락되있는지를 결정
    - 권한 승인이 필요한 부분으로 접근하려면 인증 과정을 통해 주체가 증명 되어야만 한다
    - 권한 부여에도 두가지 영역이 존재하는데 웹 요청 권한, 메소드 호출 및 도메인 인스턴스에 대한 접근 권한 부여

## Testing
### 403 
- https://stackoverflow.com/questions/21749781/why-i-received-an-error-403-with-mockmvc-and-junit

### References
- https://spring.io/blog/2014/05/07/preview-spring-security-test-method-security
- https://velog.io/@minholee_93/series/Spring-Security