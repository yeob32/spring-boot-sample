package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class SpringBootCircuitBreakerApplication

fun main(args: Array<String>) {
	runApplication<SpringBootCircuitBreakerApplication>(*args)
}
