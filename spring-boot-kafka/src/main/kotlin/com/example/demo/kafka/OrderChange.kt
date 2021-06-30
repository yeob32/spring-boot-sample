package com.example.demo.kafka

import java.time.Instant

data class OrderChange(
    val orderNo: Long,
    val createdAt: Instant
)
