package com.example.demo.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@ConfigurationProperties(prefix = "redis")
class RedisConfiguration(
    var master: RedisInstance,
    var replicas: List<RedisInstance>
)

@Component
class RedisInstance(
    var host: String = "localhost",
    var port: Int = 6379
)
