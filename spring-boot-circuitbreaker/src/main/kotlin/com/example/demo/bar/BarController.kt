package com.example.demo.bar

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BarController(
    private val barClient: BarClient,
) {
    @GetMapping("/bar")
    fun bar() {
        barClient.getBar()
    }
}