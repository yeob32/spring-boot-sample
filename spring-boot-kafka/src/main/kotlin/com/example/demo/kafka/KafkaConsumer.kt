package com.example.demo.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {
    @KafkaListener(topics = ["greeting"], groupId = "foo", containerFactory = "orderChangeListener")
    fun consume(orderChange: OrderChange) {
        System.out.printf("Consumed message : %s%n", orderChange)
    }
}