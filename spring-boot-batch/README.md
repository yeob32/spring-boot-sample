# Spring Batch

## 목표
- 스프링 배치 예제 작성
- 동적 스케줄러 구현
    - 스프링 컨테이너에 등록된 빈을 스케줄러로 등록, 실행 및 중지 

## 스프링 배치 도메인 구성
![batch-domain](https://docs.spring.io/spring-batch/docs/4.2.4.RELEASE/reference/html/images/spring-batch-reference-model.png)

### Job
배치 작업의 단위로 여러 Step으로 구성
- Next: 순서 제어 
- Flow: 조건별 흐름 제어
- Decide: Step Flow 분기
- JobScope
    - Job 실행 시점에 Bean 생성
    - JobParameter
        ```
      # 실행 
      $ java -jar build/libs/demo-0.0.1-SNAPSHOT.jar version=1
      
      $ java -jar build/libs/demo-0.0.1-SNAPSHOT.jar --job.name=someBatch scheduleDate=1
      ```
    - JobParameter LateBinding
        - Job Parameter를 StepContext 또는 JobExecutionContext 레벨에서 할당 가능
        - 어플리케이션 실행 시점 뿐만이 아닌 비즈니스 로직 처리 단계에서 Job Parameter 할당 가능
    - 동일한 컴포넌트를 병렬 혹은 동시에 사용 시 유용
        - @Scope(value = "job", proxyMode = ScopedProxyMode.TARGET_CLASS) -> 프록시 객체 주입
- preventRestart()

### Step
- Tasklet
- Reader
- Processor
    - Processor는 트랜잭션 범위 안이며, Entity의 Lazy Loading이 가능
- Writer
    - Chunk
    - JDBC writer, JPA Writer
- Reader와 Processor에서는 1건씩 다뤄지고, Writer에선 Chunk 단위로 처리

### Chunk
데이터 덩어리로 작업 할 때 각 커밋 사이에 처리되는 row 수
- Chunk 지향 처리 
    - 한 번에 하나씩 데이터를 읽어 Chunk라는 덩어리를 만든 뒤, Chunk 단위로 트랜잭션
    - 실패할 경우엔 해당 Chunk 만큼만 롤백

## 관리 도구
- Cron
- Jenkins
- api
- Quartz


      
## 이슈
```
# spring.data.jpa 에서 엔티티로 등록된 클래스의 필드로 쿼리 생성 시 repository 메서드 이름 필드 간 매칭이 안될 때 발생 
Caused by: java.lang.IllegalArgumentException: Failed to create query for method public abstract java.util.List com.example.demo.batch.domain.pay.PaymentRepository.findAllBySuccess()! No property success found for type Payment!
```


## 참조
- https://docs.spring.io/spring-batch/docs/4.2.4.RELEASE/reference/html/
- https://jojoldu.tistory.com/324?category=902551