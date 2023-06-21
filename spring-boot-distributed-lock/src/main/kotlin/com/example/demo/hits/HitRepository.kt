package com.example.demo.hits

import org.springframework.data.jpa.repository.JpaRepository

interface HitRepository: JpaRepository<Hit, Long>