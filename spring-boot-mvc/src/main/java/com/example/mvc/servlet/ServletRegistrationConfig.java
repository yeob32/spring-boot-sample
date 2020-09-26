package com.example.mvc.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

@Configuration
public class ServletRegistrationConfig {

    @Bean
    public ServletRegistrationBean<HttpServlet> getServletRegistrationBean() {
        ServletRegistrationBean<HttpServlet> registrationBean = new ServletRegistrationBean<>(new HelloServlet());
        registrationBean.addUrlMappings("/hello");
        return registrationBean;
    }
}