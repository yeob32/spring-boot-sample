package com.example.demo.order

import com.example.demo.person.Person
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllByPerson(person: Person): List<Order>
}