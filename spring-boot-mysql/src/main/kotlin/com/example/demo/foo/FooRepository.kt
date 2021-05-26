package com.example.demo.foo

import org.springframework.data.jpa.repository.JpaRepository

interface FooRepository: JpaRepository<Foo, Long>