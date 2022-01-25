package com.example.demo.bar

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException

@RestController
@RequestMapping("/v1/sample")
class SampleController {

    private var count = 0

    @GetMapping
    fun sample() {
        count++
        if(count % 5 == 0) {
            throw RuntimeException()
        } else if(count % 7 == 0) {
            Thread.sleep(3000)
        }
    }
}