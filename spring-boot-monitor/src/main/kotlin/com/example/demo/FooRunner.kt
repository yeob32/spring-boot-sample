package com.example.demo

import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class FooApp {
    @PostConstruct
    fun init() {
        val thread = Thread(FooRunner())
        thread.isDaemon = true
        thread.start()
    }
}

class FooRunner: Runnable {
    private val list = arrayListOf<String>()

    override fun run() {
        while(true) {
            Thread.sleep(500)
            println(">>>>>>>>>>>>>>")
            list.add("@")
        }
    }
}