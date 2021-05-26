package com.example.demo.bar.person

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/person")
class PersonController(
    private val personService: PersonService,
    private val personQueryRepository: PersonQueryRepository
) {

    @GetMapping("/master")
    fun getMaster() = personService.getAllMaster()

    @GetMapping("/slave")
    fun getSlave() = personService.getAllSlave()

    @PostMapping
    fun createPerson() = personService.createPerson()

    @PutMapping("/master")
    fun updateMaster() {
        personService.updateMaster()
    }

    @PutMapping("/slave")
    fun updateSlave() {
        personService.updateSlave()
    }

    @PutMapping("/slave/new1")
    fun updateSlaveAndNewTx1() {
        personService.updateSlaveNewTx1()
    }

    @PutMapping("/slave/new2")
    fun updateSlaveAndNewTx2() {
        personService.updateSlaveNewTx2()
    }

    @GetMapping("/test")
    fun test() = personQueryRepository.test()
}