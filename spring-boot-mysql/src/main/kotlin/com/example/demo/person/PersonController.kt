package com.example.demo.person

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/person")
class PersonController(
    private val personService: PersonService,
) {

    @GetMapping("/master")
    fun getMaster() = personService.getAllMaster()

    @GetMapping("/slave")
    fun getSlave() = personService.getAllSlave()

    @PutMapping("/master")
    fun updateMaster() {
        personService.updateMaster()
    }

    @PutMapping("/slave")
    fun updateSlave() {
        personService.updateSlave()
    }
}