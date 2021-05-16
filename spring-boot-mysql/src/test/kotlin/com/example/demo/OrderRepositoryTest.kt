package com.example.demo

import com.example.demo.config.DatabaseConfiguration
import com.example.demo.order.Order
import com.example.demo.order.OrderRepository
import com.example.demo.person.Person
import com.example.demo.person.PersonRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import javax.persistence.EntityManager

@Import(DatabaseConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class OrderRepositoryTest @Autowired constructor(
    var personRepository: PersonRepository,
    var orderRepository: OrderRepository,
    var entityManager: EntityManager
) {

    @Test
    fun `관계매핑 오브젝트에 의한 조회 테스트`() {
        val savePerson = personRepository.save(Person(name = "test"))
        savePerson.id shouldBe 1
        val order = Order(person = savePerson)
        orderRepository.save(order)

        val person2 = Person(id = 1)
        val orders = orderRepository.findAllByPerson(person2)
        orders.size shouldBe 1

        val savePerson2 = personRepository.save(person2)
        savePerson2.name shouldBe null
    }

    @Test
    fun `allopen 플러그인 적용하여 프록시 정상 동작 확인`() {
        orderRepository.save(Order(person = Person(name = "test")))
        entityManager.clear()

        val orders = orderRepository.findAll()
        orders.size shouldBe 1
    }
}