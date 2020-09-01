package com.example.scheduler.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@EnableScheduling
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    private static final int POOL_SIZE = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

        taskScheduler.setPoolSize(POOL_SIZE);
        taskScheduler.setThreadNamePrefix("yeob-scheduled-taskPool");
        taskScheduler.initialize();

        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    @Bean
    public ScheduledTaskRegistrar taskRegistrar() {
        ScheduledTaskRegistrar taskRegistrar = new ScheduledTaskRegistrar();

//        taskRegistrar.scheduleCronTask(new CronTask(new Task1(""), "*/1 * * * * *"));
//        taskRegistrar.scheduleFixedDelayTask(new FixedDelayTask(new Task1("FixedDelayTask"), 1000, 1000));
//        taskRegistrar.scheduleFixedRateTask(new FixedRateTask(new Task1("FixedRateTask"), 1000, 1000));

        return taskRegistrar;
    }
}
