package com.example.demo.bar

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    value = "bar",
    url = "\${api.url}/v1/sample",
    fallbackFactory = BarFallbackFactory::class
)
interface BarClient {
    @GetMapping
    fun getBar(): Iterable<Any>
}