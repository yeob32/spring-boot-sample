# Spring Boot Redis

## Redis
### Data Structure
- strings
- hashes
- lists
- sets
- sorted sets
- geo, hyperloglog

### commands
- https://redis.io/commands

### Docker Redis CLI
```
$ docker run -it --network redis-net --rm redis redis-cli -h some-redis
```

## Jedis / Lettuce
- jedis
    - Java 기반 응용프로그램 표준 드라이버
    - 여러 스레드 에서 단일 인스턴스를 공유하려고 할 때 스레드로부터 안전하지 않음.
    - 다중 스레드 환경에서의 Jedis 사용 시 Connection Pooling 이용 
        - 인스턴스 당 물리적 연결 비용 발생하므로 Redis 연결 수 증가
- Lettuce
    - 일반적으로 선호
    - netty 기반 연결 -> connection 인스턴스(StatefulRedisConnection)를 여러 쓰레드에서 공유가 가능하다. => Thread safe
    - 따라서 다중 스레드 애플리케이션은 Lettuce와 상호 작용하는 동시 스레드 수에 관계없이 단일 연결을 사용할 수 있습니다 .
- Redis 에서 연결 제한을 사용하거나 연결 수가 적절한 연결 수를 초과하여 증가하는 경우 연결 수를 제한해야 할 수 있습니다.
- https://github.com/spring-projects/spring-session/issues/789  

## spring-data-redis
- RedisMessageListenerContainer
- MessageListenerAdapter
- ChannelTopic

## Pub / Sub
- cli 접근 후 publish event some-message

## References
- https://redis.io/
- https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#cluster
- https://www.baeldung.com/spring-embedded-redis
- https://jojoldu.tistory.com/297
- http://arahansa.github.io/docs_spring/redis.html