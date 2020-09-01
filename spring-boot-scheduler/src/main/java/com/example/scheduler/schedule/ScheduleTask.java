package com.example.scheduler.schedule;

import com.example.scheduler.schedule.enums.ScheduleType;
import lombok.*;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class ScheduleTask {

    private String id;
    private String name;
    private ScheduleType scheduleType;
    private String expression;
    private long interval;
    private long initialDelay;
    private Runnable target;
    private String action;

    @Builder(builderClassName = "FixedBuilder", builderMethodName = "fixedBuilder")
    public ScheduleTask(String id, String name, ScheduleType scheduleType, long interval, long initialDelay, Runnable target, String action) {
        Assert.hasText(id, "id must not be empty");
        Assert.hasText(name, "name must not be empty");
        Assert.notNull(scheduleType, "scheduleType must not be empty");
        Assert.notNull(target, "target must not be null");

        this.id = id;
        this.name = name;
        this.scheduleType = scheduleType;
        this.interval = interval;
        this.initialDelay = initialDelay;
        this.target = target;
        this.action = action;
    }

    @Builder(builderClassName = "CronBuilder", builderMethodName = "cronBuilder")
    public ScheduleTask(String id, String name, String expression, Runnable target, String action) {
        Assert.hasText(id, "id must not be empty");
        Assert.hasText(name, "name must not be empty");
        Assert.hasText(expression, "expression must not be empty");

        this.id = id;
        this.name = name;
        this.scheduleType = ScheduleType.cron;
        this.expression = expression;
        this.target = target;
        this.action = action;
    }
}
