package com.example.demo.kafka

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kafka/v1")
class KafkaApi(
    private val kafkaProducer: KafkaProducer
) {

    @PostMapping
    fun send(@RequestBody kafkaMessage: KafkaMessage) {
        kafkaProducer.sendMessage(kafkaMessage)
    }
}

data class KafkaMessage(
    val value: Long
)