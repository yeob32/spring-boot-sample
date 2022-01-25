package com.example.demo.foo

import feign.FeignException
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.feign.FeignDecorators
import io.github.resilience4j.feign.Resilience4jFeign
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration {
    @Bean
    fun fooFeignClient(registry: CircuitBreakerRegistry): FooFeignClient {
        val circuitBreaker = registry.circuitBreaker("foo")
        val decorators = FeignDecorators.builder()
            .withCircuitBreaker(circuitBreaker)
            .withFallback(object : FooFeignClient {
                override fun getAccounts(): Iterable<Any> {
                    println("fallback : e: !!!!!!!!!")
                    throw HttpClientFallbackException("fallback : e: !!!!!!!!!")
                }
            }, FeignException::class.java).build()
        return Resilience4jFeign.builder(decorators)
            .target(FooFeignClient::class.java, "http://localhost:3000")
    }
}