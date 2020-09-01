package com.example.scheduler.schedule.schedule;

import com.example.scheduler.schedule.ScheduleService;
import com.example.scheduler.schedule.ScheduleTask;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ScheduleActionRunner implements ApplicationRunner {

    private static final String SCHEDULE = "scheduleActionRunner.test";

    private final ScheduleService scheduleService;

    public void test() {
        System.out.println("ScheduleAction !!!");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ScheduleTask scheduleTask = ScheduleTask.cronBuilder()
                .id("cron-action1")
                .name("크론 액션 테스트")
                .expression("*/1 * * * * *")
                .action(SCHEDULE).build();

        scheduleService.register(scheduleTask);
    }
}
