package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfiguration {

    @Bean
    public Employee employee() {
        System.out.println("employee been register !!");
        return new Employee("emp01");
    }
}
