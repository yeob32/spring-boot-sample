package com.example.demo.foo

import javax.persistence.*

@Entity
@Table(name = "foo")
class Foo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @Column(name = "name")
    var name: String
)