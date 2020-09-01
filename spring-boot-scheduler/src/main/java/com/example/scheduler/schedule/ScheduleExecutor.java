package com.example.scheduler.schedule;

import com.example.scheduler.schedule.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiredArgsConstructor
@Component
public class ScheduleExecutor {

    private Method method;
    private Object service;

    public ScheduleExecutor(ScheduleTask scheduleTask) {
        try {
            String clazz = scheduleTask.getAction().split("\\.")[0];
            String method = scheduleTask.getAction().split("\\.")[1];

            this.service = BeanUtil.getBean(clazz);
            this.method = service.getClass().getMethod(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        try {
            this.method.invoke(service);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
