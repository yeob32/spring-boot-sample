package com.example.mvc.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
@Component
public class MyContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("web application start !!!");

        ServletContext servletContext = sce.getServletContext();
        log.info("server info : {} ", servletContext.getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("web application end !!!");
    }
}
