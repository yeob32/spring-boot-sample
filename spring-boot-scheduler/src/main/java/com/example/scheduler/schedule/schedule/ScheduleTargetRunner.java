package com.example.scheduler.schedule.schedule;

import com.example.scheduler.schedule.ScheduleService;
import com.example.scheduler.schedule.ScheduleTask;
import com.example.scheduler.schedule.enums.ScheduleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduleTargetRunner implements ApplicationRunner {

    private final ScheduleService scheduleService;

    public ScheduleTargetRunner(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ScheduleTask scheduleTask1 = ScheduleTask.cronBuilder()
                .id("cron1")
                .name("크론 테스트")
                .target(new TaskRunner("cron !!!"))
                .expression("*/1 * * * * *")
                .build();

        ScheduleTask scheduleTask2 = ScheduleTask.fixedBuilder()
                .id("fixedDelay1")
                .name("작업 종료 후 반복 동작")
                .scheduleType(ScheduleType.fixedDelay)
                .target(() -> {
                    try {
                        log.info("Schedule Task : fixedDelay1");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .interval(10000)
                .initialDelay(60000)
                .build();

        ScheduleTask scheduleTask3 = ScheduleTask.fixedBuilder()
                .id("fixedRate1")
                .name("작업 종료 여부 상관 없이 반복 동작")
                .scheduleType(ScheduleType.fixedRate)
                .target(() -> {
                    try {
                        log.info("Schedule Task : fixedRate1");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .interval(10000)
                .initialDelay(60000)
                .build();

        scheduleService.register(scheduleTask1);
        scheduleService.register(scheduleTask2);
        scheduleService.register(scheduleTask3);
    }

    public static class TaskRunner implements Runnable {

        private final String name;

        public TaskRunner(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            log.info("Schedule Task : " + name);
        }
    }
}
