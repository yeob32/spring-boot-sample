package com.example.demo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FooApi {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/api/v1/foo")
    fun foo(): String {
        log.info("foo !!!!!!!!!")
        return "success"
    }
}