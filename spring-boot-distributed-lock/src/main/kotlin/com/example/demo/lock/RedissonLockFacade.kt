package com.example.demo.lock

import com.example.demo.hits.HitService
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedissonLockFacade(
    private val redissonClient: RedissonClient,
    private val hitService: HitService
) {
    fun increase(hitId: Long) {
        val lock = redissonClient.getLock(hitId.toString())

        try {
            val available = lock.tryLock(20, 1, TimeUnit.SECONDS)
            if (!available) {
                println("Failed to acquire lock")
                return
            }
            hitService.increase(hitId)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } finally {
            lock.unlock()
        }
    }
}