package com.example.demo.person

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/person")
class PersonController(
    private val personRepository: PersonRepository
) {

    @GetMapping("/master")
    @Transactional(readOnly = false)
    fun getMaster(): MutableList<Person> = personRepository.findAll()

    @GetMapping("/slave")
    @Transactional(readOnly = true)
    fun getSlave(): MutableList<Person> = personRepository.findAll()
}