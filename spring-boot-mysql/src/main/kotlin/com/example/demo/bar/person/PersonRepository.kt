package com.example.demo.bar.person

import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository: JpaRepository<Person, Long>