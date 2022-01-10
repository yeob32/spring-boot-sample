package com.example.demo

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Component

@Component
class FooClientWrapper(private val fooClient: FooClient) {
    @CircuitBreaker(name = "foo", fallbackMethod = "fallback")
    fun getAccounts(): Iterable<Any> {
        return fooClient.getAccounts()
    }

    private fun fallback(e: Exception): Iterable<Any> {
        println("fallback : e: ${e.message}")
        throw HttpClientFallbackException("fallback : e: ${e.message}")
    }
}