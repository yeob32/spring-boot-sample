# Spring Boot Initialize

## 스프링 부트 초기화 방법 
- Application Runner
- CommandLineRunner
- ApplicationReadyEvent
- @EventListener

## References
- https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#validation

## 이슈
### 서브 모듈 참조 시 컴파일 에러
```
module: spring-boot-initialize

dependencies {
    implementation project(':spring-boot-configuration')
}

// 아래 코드 추가해야함.
jar {
    enabled = true
}
```

```

> Task :spring-boot-initialize:compileJava FAILED
3 actionable tasks: 1 executed, 2 up-to-date
/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/src/main/java/com/example/demo/config/AppConfig.java:20: error: cannot find symbol
    public Company company(CompanyProperties companyProperties) { // 왜 안되냐....ㅋㅋㅋ
                           ^
  symbol:   class CompanyProperties
  location: class AppConfig
/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/src/main/java/com/example/demo/config/AppConfig.java:20: error: cannot find symbol
    public Company company(CompanyProperties companyProperties) { // 왜 안되냐....ㅋㅋㅋ
           ^
  symbol:   class Company
  location: class AppConfig
/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/src/main/java/com/example/demo/SpringInitializeApplication.java:3: error: cannot find symbol
import com.example.demo.config.Company;
                              ^
  symbol:   class Company
  location: package com.example.demo.config
/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/src/main/java/com/example/demo/SpringInitializeApplication.java:4: error: cannot find symbol
import com.example.demo.config.CompanyProperties;
                              ^
  symbol:   class CompanyProperties
  location: package com.example.demo.config
/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/src/main/java/com/example/demo/SpringInitializeApplication.java:5: error: cannot find symbol
import com.example.demo.config.Employee;
                              ^
  symbol:   class Employee
  location: package com.example.demo.config
/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/src/main/java/com/example/demo/config/AppConfig.java:19: error: cannot find symbol
    @ConditionalOnBean(CompanyConfiguration.class) // 빈 등록 순서가 autoConfiguration 보다 빠르기 때문에 초기화 빈 없다요?
                       ^
  symbol:   class CompanyConfiguration
  location: class AppConfig
/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/src/main/java/com/example/demo/SpringInitializeApplication.java:23: error: cannot find symbol
@EnableConfigurationProperties(CompanyProperties.class)
                               ^
  symbol: class CompanyProperties
7 errors

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':spring-boot-initialize:compileJava'.
> Compilation failed; see the compiler error output for details.

```

### @SpringBootConfiguration 다중 로드
- 서브 모듈(spring-boot-configuration)의 @SpringBootConfiguration 같이 동작하나보다..
```
@SpringBootTest(classes = SpringInitializeApplication.class)
class SpringInitializeApplicationTests {
	@Test
	void contextLoads() {
	}
}
```
```
Found multiple @SpringBootConfiguration annotated classes [Generic bean: class [com.example.demo.SpringInitializeApplication]; scope=; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in file [/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/build/classes/java/main/com/example/demo/SpringInitializeApplication.class], Generic bean: class [com.example.demo.SpringConfigurationApplication]; scope=; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in URL [jar:file:/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-configuration/build/libs/spring-boot-configuration-0.0.1-SNAPSHOT.jar!/com/example/demo/SpringConfigurationApplication.class]]
java.lang.IllegalStateException: Found multiple @SpringBootConfiguration annotated classes [Generic bean: class [com.example.demo.SpringInitializeApplication]; scope=; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in file [/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-initialize/build/classes/java/main/com/example/demo/SpringInitializeApplication.class], Generic bean: class [com.example.demo.SpringConfigurationApplication]; scope=; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in URL [jar:file:/Users/sykim/IdeaProjects/spring-boot-tutorial/spring-boot-configuration/build/libs/spring-boot-configuration-0.0.1-SNAPSHOT.jar!/com/example/demo/SpringConfigurationApplication.class]]
```