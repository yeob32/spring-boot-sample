package com.example.demo.bar.order

import com.example.demo.bar.person.Person
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllByPerson(person: Person): List<Order>
}