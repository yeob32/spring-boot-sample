package com.example.scheduler.schedule;

import com.example.scheduler.schedule.enums.ScheduleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.config.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduledTaskRegistrar taskRegistrar;
    private final Map<String, ScheduledTask> scheduleTasks = new ConcurrentHashMap<>();

    public void register(ScheduleTask scheduleTask) {
        log.info("register : " + scheduleTask);

        String scheduleTaskId = scheduleTask.getId();
        ScheduleType scheduleType = scheduleTask.getScheduleType();
        Runnable runnable = scheduleTask.getTarget();
        if (runnable == null) {
            runnable = new ScheduleRunner(scheduleTask);
        }

        switch (scheduleType) {
            case cron:
                scheduleTasks.put(scheduleTaskId, taskRegistrar.scheduleCronTask(new CronTask(runnable, scheduleTask.getExpression())));
                break;
            case fixedRate:
                scheduleTasks.put(scheduleTaskId, taskRegistrar.scheduleFixedDelayTask(new FixedDelayTask(runnable, scheduleTask.getInterval(), scheduleTask.getInitialDelay())));
                break;
            case fixedDelay:
                scheduleTasks.put(scheduleTaskId, taskRegistrar.scheduleFixedRateTask(new FixedRateTask(runnable, scheduleTask.getInterval(), scheduleTask.getInitialDelay())));
                break;
        }
    }

    public void stop(String key) {
        scheduleTasks.get(key).cancel();
        log.info("stop : " + key);
    }
}
