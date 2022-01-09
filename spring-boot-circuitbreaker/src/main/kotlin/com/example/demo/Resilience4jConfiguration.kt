package com.example.demo

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer
import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer
import io.github.resilience4j.retry.RetryConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

//@Configuration
class Resilience4jConfiguration {

    @Bean
    fun fooCircuitBreakerCustomizer(): CircuitBreakerConfigCustomizer {
        return CircuitBreakerConfigCustomizer
            .of("foo") { builder: CircuitBreakerConfig.Builder -> builder.slidingWindowSize(3) }
    }

    @Bean
    fun fooRetryConfigCustomizer(): RetryConfigCustomizer {
        return RetryConfigCustomizer.of("foo") { it.intervalFunction { 10 }.maxAttempts(3).build() }
    }

    @Bean
    fun globalCustomCircuitBreakerConfiguration(): Customizer<Resilience4JCircuitBreakerFactory> {
        val circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(5f)
            .waitDurationInOpenState(Duration.ofSeconds(5))
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(2)
            .build()
        val timeLimiterConfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(4))
            .build()

        return Customizer<Resilience4JCircuitBreakerFactory> { factory ->
            factory.configureDefault { id ->
                Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(timeLimiterConfig)
                    .circuitBreakerConfig(circuitBreakerConfig)
                    .build()
            }
        }
    }
}