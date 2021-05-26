package com.example.demo.foo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/foo")
class FooController(private val fooRepository: FooRepository) {

    @GetMapping
    fun getFoo() = fooRepository.findAll()

    @PostMapping
    fun createFoo() = fooRepository.save(Foo(name = "foooooo"))
}