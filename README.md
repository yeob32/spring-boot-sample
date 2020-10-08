# Spring Boot 

## Why?
- 자바 및 JVM 환경의 대체 언어들의 효율적이고 쉬운 엔터프라이즈 애플리케이션 개발 환경 제공
- 만들고자하는 애플리케이션의 요구사항과 목적에 따라 유연하게 적용 가능
- 패키지들간의 순환 의존성이없는 깨끗한 프로젝트구성 가능
- 직관적인 API를 제공
- 오픈소스로써 지속적인 업데이트, 이전 버전들에 대한 강력한 호환성 지원


## Gradle
### binary plugin
- buildscripts
    - 고전적인 방식
- plugins
    - 빌드스크립트(buildscripts) 플러그인 선언부를 이해할 수 있는 형태로 개선
    - gradle 4.6 ~
    

## 이슈

### build Error
```
> Task :bootJar FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':bootJar'.
> org.gradle.api.GradleException: Failed to resolve imported Maven boms: Cannot resolve external dependency org.springframework.boot:spring-boot-dependencies:2.3.4.RELEASE because no repositories are defined.

> Could not resolve all dependencies for configuration ':detachedConfiguration1'.
   > Cannot resolve external dependency org.springframework.boot:spring-boot-dependencies:2.3.3.RELEASE because no repositories are defined.
     Required by:
         project :

```

### 해결
- gradle 빌드 시 각 프로젝트를 실행가능한 jar 형태로 만드나 상위 모듈에는 Main 메서드가 없어서 에러 발생
- 상위 모듈에서 bootJar task 비활성화
```
bootJar {
    enabled = false
}

jar {
    enabled = true
}
```