package com.example.demo.bar.order

import com.example.demo.bar.person.Person
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var createdAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "person_id")
    var person: Person
)