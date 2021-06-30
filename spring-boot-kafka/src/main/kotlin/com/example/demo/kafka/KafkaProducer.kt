package com.example.demo.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, OrderChange>) {

    companion object {
        private const val TOPIC = "greeting"
    }

    fun sendMessage(kafkaMessage: KafkaMessage) {
        System.out.printf("Produce message : %s%n", kafkaMessage.value)
        kafkaTemplate.send(TOPIC, OrderChange(
            orderNo = kafkaMessage.value,
            createdAt = Instant.now()
        ))
    }
}