package com.example.demo

import com.example.demo.person.Person
import com.example.demo.person.PersonRepository
import com.example.demo.person.PersonService
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import javax.persistence.EntityManager

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class PersonServiceTest @Autowired constructor(
    var personRepository: PersonRepository,
    var personService: PersonService,
    var entityManager: EntityManager
) {
    @BeforeEach
    fun init() {
        entityManager.clear()
        personRepository.deleteAll()
        createPerson()
    }

    @Test
    fun `마스터 디비에서는 데이터 변경 가능`() {
        personService.updateMaster()
        val persons = personService.getAllSlave()
        persons[0].name shouldBe "name master"
    }

    @Test
    fun `슬레이브 디비에서는 데이터 변경 불가능`() {
        personService.updateSlave()
        val persons = personService.getAllSlave()
        persons[0].name shouldBe "ksy"
    }

    @Test
    fun `슬레이브 및 다른 빈에서 새 트랜잭션의 경우 변경 가능`() {
        personService.updateSlaveNewTx1()
        val persons = personService.getAllSlave()
        persons[0].name shouldBe "name slave new transaction1"
    }

    @Test
    fun `슬레이브 및 동일 빈에서 새 트랜잭션의 경우 변경 불가능`() {
        personService.updateSlaveNewTx2()
        val persons = personService.getAllSlave()
        persons[0].name shouldBe "ksy"
    }

    private fun createPerson(): Person = personRepository.save(Person(name = "ksy"))
}

