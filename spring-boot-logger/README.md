# Logging

## Logback
- 오픈 소스 로깅 프레임워크, SLF4J 구현체
- spring boot 기본 설정
  - spring-boot-starter
    - spring-boot-starter-logging

## MDC
- Mapped Diagnostic Context
- Thread Local 사용하여 로깅
  - 멀티 쓰레드 환경에서의 클라이언트 추적 도와줌 

## AOP
- spring-boot-starter-aop 의존 추가
- @EnableAspectJAutoProxy 설정 필요하지만.. 스타터 의존 모듈에 자동 설정 되어 있다 
  - spring-boot-starter : spring-boot-autoconfigure 
    - aop.AopAutoConfiguration.class
  
### 용어
- Aspect
  - 여러 객체에 공통으로 적용되는 기능. 트랜젝션, 보안 등
- Advice
  - 언제 공통로직을 핵심 로직에 적용할지를 정의하는 것.
- JointPoint
  - Advice를 적용 가능한 지점으로, 스프링은 프록시를 이용하여 AOP를 구현하기 때문에 메서드 호출에 대한 JointPoint를 지원합니다.
- PointCut
  - JoinPoint의 부분 집합으로, 실제 Advice가 적용되는 JoinPoint를 나타냅니다. 스프링은 정규식이나 Aspect 문법을 이용하여 Pointcut에 정의할 수 있습니다.
