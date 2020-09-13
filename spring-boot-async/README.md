# spring boot async

## 개요
- 스프링 부트에서의 비동기 동작을 알아본다.
- @Async, @EventListener

### 동기 방식의 문제점
- 트랜잭션 문제 -> 해당 메서드 에러 발생 시 주문 롤백 됨
- 성능 문제 -> 동기 방식이기 때문에 해당 메서드 처리 지연 시 주문 프로세스 처리 지연

### 비동기, 논블로킹
- 간단하게 논블로킹  
    
### CompletableFuture
- TODO

## @EnableAsync / @Async
- @Async 
    - public 메서드에만 적용
        - public 이어야 프록시가 될 수 있음
    - self invocation 이면 작동 안함
        - 프록시를 우회하고 해당 메소드를 직접 호출하기 때문


### Async Task Example
```
@EnableAsync
@SpringBootApplication
public class Application {
    
}

public class SomeService {
    @Async
    public void method(String message) throws Exception {
        // do something
    }
}
```

## TaskExecutor
### SimpleAsyncTaskExecutor -> 스프링 @EnableAsync 기본값
- 어떤 스레드도 재사용하지 않고 호출마다 새로운 스레드를 시작
- 동시접속 제한(concurrency limit)을 지원 제한 수가 넘어서면 빈 공간이 생길 때까지 모든 요청 block
### SyncTaskExecutor
- 호출을 비동기적으로 수행하지 않는다. 대신, 각각의 호출은 호출 쓰레드로 대체된다.  
- 간단한 테스트케이스와 같이 필요하지 않은 멀티쓰레드 상황에서 사용된다.
### SimpleThreadPoolTaskExecutor
- Spring의 생명주기 콜백을 듣는 Quartz의 SimpleThreadPool의 하위클래스.
- Quartz와 Quartz가 아닌 컴포넌트간에 공유될 필요가 있는 쓰레드 풀
### ThreadPoolTaskExecutor
- 자바 5에서 가장 일반적으로 사용.
- java.util.concurrent.ThreadPoolExecutor를 구성하는 bean 프로퍼티를 노출하고 이를 TaskExecutor로 감싼다.
### TimerTaskExecutor
- 지원되는 구현물 중 하나의 TimerTask를 사용.
- 쓰레드에서 동기적이더라도 메소드 호출이 개별 쓰레드에서 수행되어 SyncTaskExecutor와 다르다.
### WorkManagerTaskExecutor
- 지원되는 구현물 중 하나로 CommonJ WorkManager을 사용
- Spring 컨텍스트에서 CommonJ WorkManager참조를 셋팅하기 위한 중심적이고 편리한 클래스
- SimpleThreadPoolTaskExecutor와 유사하게, 이 클래스는 WorkManager인터페이스를 구현하고 WorkManager만큼 직접 사용

### Thread Pool Configuration
```
@Bean
public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(8);
    threadPoolTaskExecutor.setMaxPoolSize(8);
    threadPoolTaskExecutor.setThreadNamePrefix("yeob-pool-task");

    return threadPoolTaskExecutor;
}
```

## @EventListener
- 느슨한 결합 구성
```
public class SomeEvent {
    private final SomeObject;
}

@RequiredArgsConstructor
@Component
public class CouponEventHandler {

    private final SomeService someService;

    @EventListener
    public void handleCouponEvent(SomeEvent someEvent) {
        SomeObject SomeObject = someEvent.getSomeObject();
        someService.someMethod(SomeObject);
    }
}
```

## @Async, @TransactionalEventListener
- @Async: 비동기 처리, 해당 메서드는 기존 스레드와 분리
- @TransactionalEventListener: 해당 트랜잭션이 Commit 된 이후에 리스너가 동작
- @Order: 이벤트 실행 순서
```
@RequiredArgsConstructor
@Component
public class AccountEventHandler {

    private final EmailSendService emailSendService;

    @Order(1)
    @Async
    @TransactionalEventListener 
    public void handleAccountEvent(AccountEvent accountEvent) {
        emailSendService.sendSignUpEmail(accountEvent.getAccount());
    }
}
```

## References
- https://medium.com/@pakss328/spring-async-annotation%EC%9D%84-%ED%99%9C%EC%9A%A9%ED%95%9C-thread-%EA%B5%AC%ED%98%84-f5b4766d49c5