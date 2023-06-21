package com.example.demo.lock

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        private const val REDIS_LOCK_KEY = "LOCK_"
    }

    fun lock(key: Long): Boolean {
        return redisTemplate
            .opsForValue()
            .setIfAbsent("$REDIS_LOCK_KEY$key", "locked", Duration.ofMillis(1000)) ?: false
    }

    fun unlock(key: Long): Boolean {
        return redisTemplate.delete(key.toString())
    }
}