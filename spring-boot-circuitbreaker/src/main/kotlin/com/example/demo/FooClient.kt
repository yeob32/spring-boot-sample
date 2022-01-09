package com.example.demo

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(value = "accounts", url = "http://localhost:3000/v1/accounts")
interface FooClient {

    @CircuitBreaker(name = "foo-test")
    @Retry(name = "foo-test")
//    @CircuitBreaker(name = "foo", fallbackMethod = "fallback")
    @GetMapping
    fun getAccounts(): Iterable<Any>

    private fun fallback(p1: String, e: Exception): String {
        println("fallback : p1 : $p1, e: ${e.message}")
        return "fallback"
    }
}