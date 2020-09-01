package com.example.scheduler.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ScheduleRunner implements Runnable {

    private final ScheduleTask scheduleTask;

    @Override
    public void run() {
        try {
            new ScheduleExecutor(scheduleTask).execute();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
