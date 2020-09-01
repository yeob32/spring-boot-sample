package com.example.scheduler.schedule.api;

import com.example.scheduler.schedule.ScheduleService;
import com.example.scheduler.schedule.ScheduleTask;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SchedulerApi {

    private final ScheduleService scheduleService;

    @GetMapping(value = "/schedule/stop")
    public void cancelSchedule(@RequestParam("id") String id) {
        scheduleService.stop(id);
    }

    @PostMapping(value = "/schedule/register")
    public void registerSchedule(String id, String name, String expression, String actionTarget) {
        ScheduleTask scheduleTask = ScheduleTask.cronBuilder()
                .id(id)
                .name(name)
                .expression(expression)
                .action(actionTarget)
                .build();

        scheduleService.register(scheduleTask);
    }
}
