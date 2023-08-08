# Distributed Lock

### Lock

- Atomic
    - Lock 존재 확인 -> 존재하지 않는다면 획득

## Atomic

- 원자성을 보장하는 변수
- CAS(Compared And Swap)
  - NonBlocking 상태로 동기화 문제 해결 가능
- 멀티쓰레드 환경에서 동기화 문제를 synchronized 키워드를 사용하여 락을 거는데, 해당 키워드 없이 동기화 문제를 해결하기 위해 고안된 방법

## Synchronized

- 멀티스레드 환경에서 스레드간 데이터 동기화 (thread-safe)
- 데이터를 선점하여 순차적으로 접근
- 각 프로세스의 동시 접근만 제어하기 떄문에 서버 2대 이상일 경우 사용 어려움
- 특정 스레드가 해당 블럭 전체를 lock 하기 때문에, 다른 Thread는 아무런 작업을 하지 못하고 기다리는 상황 발생

## DB Lock

### 비관적 락 (pessimistic_lock)

- 특징
    - 트랜잭션의 충돌이 발생한다고 가정하고 우선 락(Lock)을 걸고 본다.
    - 한 트랜잭션 내용이 완료되기 전까지 다른 트랜잭션이 공유자원에 접근하지 못함
        - 동시성이 떨어져 대기가 길어지고 성능이 떨어질 수 있다.
    - 각 트랜잭션이 서로 자원을 점유한 채, 서로의 자원을 요청하며 무한정 대기하는 데드락(DeadLock) 유발 가능성
    - 충돌이 빈번하게 일어난다면 롤백의 횟수를 줄일 수 있기 때문에,낙관적 락 보다는 성능이 좋을 수 있다.
- **공유 락 (Shared Lock)**
    - == 읽기 락(Read Lock)
    - 공유 락이 걸린 데이터에 대해서는 읽기 연산(SELECT) 만 가능, 쓰기 연산은 불가능
    - 조회한 데이터가 트랜잭션 안에서 변경되지 않음을 보장
- **배타 락 (Exclusive Lock)**
    - == 쓰기 락(Write Lock)
    - 락이 걸려 다른 트랜잭션에서 읽기, 쓰기 작업을 진행하지 못하게 되는 블로킹(Blocking) 상태가 됨

### 낙관적 락 (optimistic_lock)

- 충돌이 없다고 가정하고, 별도의 락을 잡지 않으므로 비관적 락 보다는 성능적 이점
- 낙관적 락의 최대 단점은 롤백 (성능 이슈)
    - 충돌을 해결하려면 개발자가 수동으로 롤백처리를 해줘야 한다.
        - `ObjectOptimisticLockingFailureException` 발생하여 `@Transactional` 통해서 롤백됨
        - 오류 잡아서 재시도 처리 필요성 고려
- 낙관적 락은 충돌이 많이 예상되거나 충돌이 발생했을 때 비용이 많이 들것이라고 판단되는 곳에서는 사용하지 않는 것이 좋다.

### DeadLock 해결방안

- 트랜잭션 진행방향을 같은방향으로 처리
- 트랜잭션 처리속도를 최소화해서, 데드락에 빠지는 상황을 방지
- LOCK TIMEOUT 을 이용하여 락 해제 시간을 조절해서 데드락으로부터 빠져나오게 한다.

## Redis Lock

### Lettuce

- Spin Lock
    - Lock 을 획득하기 위해 계속해서 Redis 에 요청을 보내게 되는 것이므로 스레드 혹은 프로세스가 많다면 Redis 에 부하
- `setnx`
    - 값이 존재하지 않으면 세팅한다
    - 값 세팅 여부로 락 획득 여부 확인
- `del`
    - Lock 해제
- 개발자가 직접 Lock 관련 로직 구현해야함
- 재시도가 필요가 없는 경우에 사용
- 별도의 timeout 이 없고, 설정된 expire 값이 시간 동안 Lock 획득

```shell
127.0.0.1:6379> setnx LOCK_1 locked
(integer) 1
127.0.0.1:6379> setnx LOCK_1 locked
(integer) 0
127.0.0.1:6379> del LOCK_1
(integer) 1
127.0.0.1:6379> setnx LOCK_1 locked
(integer) 1
```

### Redisson

- timeout 구현 되어 있음
- pub/sub 기능 사용으로 오버헤드 줄였음
    - Lock 해제 시 subscribe client 에게 락 해제 알림
- 분산락 해제 시점과 트랜잭션 커밋 시점의 불일치
- 재시도가 필요한 경우에 사용
