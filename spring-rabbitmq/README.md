# Spring Boot RabbitMQ

## 개요
### RabbmitMQ
- 오픈소스 AMQP 브로커

### AMQP (Advanced Message Queuing Protocol)
- 시스템 간 메시지를 교환하기 위해 공개 표준으로 정의한 프로토콜

### Term
- Queue
    - 메시지를 메모리나 디스크에 저장했다가 Cusomer에게 메시지를 전달
    - 메시지가 소비되기 전 대기하고 있는 최종 지점으로 Exchange 라우팅 규칙에 의해 단일 메시지가 복사되거나 다수의 큐에 도달할 수 있다
- Exchange
    - Publisher(Producer)로부터 수신한 메시지를 큐에 분배하는 라우터
    - 발행한 모든 메시지가 처음 도달하는 지점으로 메시지가 목적지에 도달할 수 있도록 라우팅 규칙 적용, 라우팅 규칙에는 direct, topic, fanout
- Binding
    - Exchange가 Queue에게 메시지를 전달하기 위한 룰
    - 빈으로 등록한 Queue와 Exchange를 바인딩하면서 Exchange에서 사용될 패턴을 설정해 주었습니다.
- Broker 
    - 발행자가 만든 메시지를 저장
- Virtual host
    - Broker 내의 가상 영역
- Connection
    - 발생자와 소비자, Broker 사이의 물리적인 연결
- Channel
    - 발행자와 소비자, Broker 사이의 논리적인 연결, 하나의 Connection 내에 다수의 Channel 설정 가능

## References
- https://spring.io/guides/gs/messaging-rabbitmq/

## 준비
### Dependency
```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-amqp'
}
```

### Docker 
```
-- 5672 port 는 rabbitMQ 통신 시 사용
docker run -d --hostname rabbit --name rabbit -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=sykim -e RABBITMQ_DEFAULT_PASS=1234 rabbitmq:3-management
```