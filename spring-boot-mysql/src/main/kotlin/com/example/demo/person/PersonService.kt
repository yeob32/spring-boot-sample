package com.example.demo.person

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonService(
    private val personRepository: PersonRepository
) {

    @Transactional(readOnly = true)
    fun getAllSlave() = personRepository.findAll()

    @Transactional(readOnly = false)
    fun getAllMaster() = personRepository.findAll()

    @Transactional(readOnly = true)
    fun updateSlave() {
        updateName("name slave")
    }

    @Transactional(readOnly = false)
    fun updateMaster() {
        updateName("name master")
    }

    @Transactional(readOnly = false)
    fun updateName(name: String) {
        personRepository.findAll().forEach {
            it.name = name
        }
    }
}