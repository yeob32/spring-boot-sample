# Redis

## Distributed Lock

### Lock
- Atomic
  - Lock 존재 확인 -> 존재하지 않는다면 획득

### Lettuce
- Spin Lock
  - Lock 을 획득하기 위해 계속해서 Redis 에 요청을 보내게 되는 것이므로 스레드 혹은 프로세스가 많다면 Redis 에 부하
- `setnx`
  - 값이 존재하지 않으면 세팅한다
  - 값 세팅 여부로 락 획득 여부 확인
- `del`
  - Lock 해제
- 개발자가 직접 Lock 관련 로직 구현해야함

### Redisson
- timeout 구현 되어 있음
- pub/sub 기능 사용으로 오버헤드 줄였음
  - Lock 해제 시 subscribe client 에게 락 해제 알림 
- 분산락 해제 시점과 트랜잭션 커밋 시점의 불일치