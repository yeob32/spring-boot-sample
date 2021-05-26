package com.example.demo

import com.example.demo.bar.person.Person
import com.example.demo.bar.person.PersonRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Component
class ApplicationSetup(
    private val personRepository: PersonRepository,
) {
//    @Bean
    fun applicationRunner() = ApplicationRunner {
        personRepository.saveAll(
            arrayListOf(Person(name = "test1"), Person(name = "test2"))
        )
    }
}