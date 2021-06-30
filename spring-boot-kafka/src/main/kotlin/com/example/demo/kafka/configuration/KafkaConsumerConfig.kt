package com.example.demo.kafka.configuration

import com.example.demo.kafka.OrderChange
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig {

    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    private lateinit var bootstrapServers: String
    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    @Bean
    fun orderChangeConsumerFactory(): ConsumerFactory<String, OrderChange> = DefaultKafkaConsumerFactory(
        hashMapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to groupId
        ),
        StringDeserializer(),
        JsonDeserializer(OrderChange::class.java)
    )

    @Bean
    fun orderChangeListener(): ConcurrentKafkaListenerContainerFactory<String, OrderChange> =
        ConcurrentKafkaListenerContainerFactory<String, OrderChange>().apply {
            consumerFactory = orderChangeConsumerFactory()
        }
}