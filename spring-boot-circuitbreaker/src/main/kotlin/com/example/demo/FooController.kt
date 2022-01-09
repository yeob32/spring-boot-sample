package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FooController(private val fooClient: FooClient) {

    @GetMapping
    fun foo() = try {
        val a= fooClient.getAccounts()
        println("result : $a")
        a
    } catch (e: Exception) {
        println("error : ${e.message}")
        e.message
    }
}