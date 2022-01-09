package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux

class AppTest {

    @Test
    fun `블로킹`() {
        for (i in 0..100) {
            val restTemplate = RestTemplate()
            restTemplate.getForObject("http://localhost:8080/api/v1/car", String::class.java)
        }
    }

    @Test
    fun `논블로킹`() {

        for (i in 0..100) {
            WebClient.create("http://localhost:8080/api/v1/car").get()
                .retrieve()
                .bodyToMono(String::class.java)
                .subscribe {
                    println(">>>>>>>>>>>>>>>>>>>>>>>>. $it")
                }
        }

    }
}