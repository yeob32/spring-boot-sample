package com.example.demo.configuration

import com.example.demo.hits.HitRedis
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.lettuce.core.ReadFrom
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisTemplateConfiguration(
    private val redisConfiguration: RedisConfiguration
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val clientConfig: LettuceClientConfiguration = LettuceClientConfiguration.builder()
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .build()
        val staticMasterReplicaConfiguration = RedisStaticMasterReplicaConfiguration(
            redisConfiguration.master.host,
            redisConfiguration.master.port
        )
        redisConfiguration.replicas.forEach { slave ->
            staticMasterReplicaConfiguration.addNode(
                slave.host,
                slave.port
            )
        }
        return LettuceConnectionFactory(staticMasterReplicaConfiguration, clientConfig)
    }

    @Bean
    @Primary
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        return RedisTemplate<String, String>().apply {
            this.connectionFactory = connectionFactory

            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = StringRedisSerializer()
        }
    }

    @Bean
    fun jsonRedisTemplate(): RedisTemplate<String, Any> = getRedisTemplate()

    @Bean
    fun hitHashRedisTemplate(): RedisTemplate<String, HitRedis> = getRedisTemplate()

    private inline fun <reified T> getRedisTemplate(): RedisTemplate<String, T> {
        return RedisTemplate<String, T>().apply {
            val serializer = Jackson2JsonRedisSerializer(T::class.java)
            serializer.setObjectMapper(jacksonObjectMapper())

            this.connectionFactory = redisConnectionFactory()

            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = serializer
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = serializer
        }
    }
}