package com.example.demo.person

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val personUpdateService: PersonUpdateService
) {

    @Transactional(readOnly = true)
    fun getAllSlave(): List<Person> = personRepository.findAll()

    @Transactional(readOnly = false)
    fun getAllMaster(): List<Person> = personRepository.findAll()

    @Transactional(readOnly = true)
    fun updateSlave() {
        updateName("name slave")
    }

    @Transactional(readOnly = false)
    fun updateMaster() {
        updateName("name master")
    }

    @Transactional(readOnly = true)
    fun updateSlaveNewTx1() {
        println("updateSlaveNewTx1 ${TransactionSynchronizationManager.getCurrentTransactionName()}")
        personUpdateService.updateName("name slave new transaction1")
    }

    @Transactional(readOnly = true)
    fun updateSlaveNewTx2() {
        println("updateSlaveNewTx2 ${TransactionSynchronizationManager.getCurrentTransactionName()}")
        updateNameNewTx("name slave new transaction2")
    }

    @Transactional(readOnly = false)
    fun updateName(name: String) {
        personRepository.findAll().forEach {
            it.name = name
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    fun updateNameNewTx(name: String) {
        println("PersonService updateNameNewTx ${TransactionSynchronizationManager.getCurrentTransactionName()}")
        personRepository.findAll().forEach {
            it.name = name
        }
    }

    fun createPerson() = personRepository.save(Person(name = "test person"))
}

@Service
class PersonUpdateService(
    private val personRepository: PersonRepository
) {
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    fun updateName(name: String) {
        println("PersonUpdateService updateName ${TransactionSynchronizationManager.getCurrentTransactionName()}")
        personRepository.findAll().forEach {
            it.name = name
        }
    }
}