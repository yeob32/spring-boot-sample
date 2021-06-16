package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

// spring-boot-starter > spring-boot-autoconfigure > aop.AopAutoConfiguration.class
// 꼭 명시해줄 필요는 없다 ?
@EnableAspectJAutoProxy
@SpringBootApplication
class SpringBootLoggerApplication

fun main(args: Array<String>) {
    runApplication<SpringBootLoggerApplication>(*args)
}
