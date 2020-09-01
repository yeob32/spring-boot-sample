package com.example.scheduler.schedule.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointSchedule {

    public void expiredPoint() {
        log.info("Schedule Task : expired point !! ");
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void cronJobTask() {
        System.out.println("scheduled cronJob");
    }

    @Scheduled(fixedDelay = 1000) // 종료 후 실행
    public void scheduleFixedDelayTask() {
        System.out.println("scheduleFixedDelayTask !");
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
        System.out.println("scheduleFixedRateTask !");
    }
}
