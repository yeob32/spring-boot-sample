package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@SpringBootApplication
class SpringBootMonitorApplication

fun main(args: Array<String>) {
    runApplication<SpringBootMonitorApplication>(*args)
}

@Component
class AppRunner {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun applicationRunner(): ApplicationRunner {
        return ApplicationRunner {
            for(i in 0..100) {
                log.info("spring-boot-elk-1 >>> $i")
            }
        }
    }
}
