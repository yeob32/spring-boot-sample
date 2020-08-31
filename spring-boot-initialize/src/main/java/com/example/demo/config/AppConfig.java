package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * 스프링부트 2.1
     * spring.main.allow-bean-definition-overriding=false
     * Error 발생
     *
     * @return
     */
    @Bean
    @ConditionalOnBean(CompanyConfiguration.class) // 빈 등록 순서가 autoConfiguration 보다 빠르기 때문에 초기화 빈 없다요?
    public Company company(CompanyProperties companyProperties) { // 왜 안되냐....ㅋㅋㅋ
        System.out.println("name : " + companyProperties.getName());
        System.out.println("override bean test");
        return new Company("my company");
    }

    @Bean
    @ConditionalOnMissingBean
    public String test() {
        System.out.println("regist bean test1");
        return "test!!";
    }

    @Bean
    @ConditionalOnBean(name = "test")
    public String test2() {
        System.out.println("regist bean test2");
        return "test!!";
    }

    @Bean
    @ConditionalOnBean(AppConfig.class)
    public String test3() {
        System.out.println("regist bean test3");
        return "test!!";
    }
}
