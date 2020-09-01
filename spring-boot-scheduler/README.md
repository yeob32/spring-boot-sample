# Spring Boot Scheduler

## 목표
- 동적 스케줄러 구현
    - 스프링 컨테이너에 등록된 빈을 스케줄러로 등록, 실행 및 중지 

## 구현
- ThreadPoolTaskScheduler 빈 등록
- ScheduledTaskRegistrar
    - Bean 또는 Runnable 을 구현하여 스케줄링

## 스프링 스케줄링 지원
- @EnableScheduling
- @Scheduled
    - cron
    - fixedDelay
        - 종료 후 실행
    - fixedRate
        - 이벤트 종료 여부와 관계 없이 주기적으로 실행
        
## 띠융
- 앱 구동 시 스케줄링 대상 초기화 방식 개선 .....

## 참조
- https://thebackendguy.com/spring-schedule-tasks-or-cron-jobs-dynamically/