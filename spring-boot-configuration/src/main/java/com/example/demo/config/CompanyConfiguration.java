package com.example.demo.config;

import com.example.demo.config.annotation.EnableEmployee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEmployee
// @import 하지 않았을 때 다른 모듈에서 빈 사용 시 spring.factories 등록해줘야함 -> com.example.configuration.config.EmployeeConfiguration
public class CompanyConfiguration {

    @Bean
    public Company company() {
        System.out.println("company been register !!");
        return new Company("yeob32");
    }
}
