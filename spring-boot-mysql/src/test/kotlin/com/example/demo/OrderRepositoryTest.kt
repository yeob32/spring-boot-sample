package com.example.demo

import com.example.demo.config.DatabaseConfiguration
import com.example.demo.order.Order
import com.example.demo.order.OrderRepository
import com.example.demo.person.Person
import com.example.demo.person.PersonRepository
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(DatabaseConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class OrderRepositoryTest @Autowired constructor(
    var personRepository: PersonRepository,
    var orderRepository: OrderRepository
) {

    @Test
    fun `관계매핑 흠`() {
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
}