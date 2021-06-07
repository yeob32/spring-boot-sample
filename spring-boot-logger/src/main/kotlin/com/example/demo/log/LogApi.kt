package com.example.demo.log

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/log")
@RestController
class LogApi {

    companion object : Logger

    @GetMapping
    fun log() {
        log.info("log api !")
    }
}