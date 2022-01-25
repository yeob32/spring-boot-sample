package com.example.demo.bar

import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component

@Component
class BarFallbackFactory : FallbackFactory<BarClient> {
    override fun create(cause: Throwable): BarClient {
        return object : BarClient {
            override fun getBar(): Iterable<Any> {
                return listOf("Fooooooooooooooooo!")
            }
        }
    }
}