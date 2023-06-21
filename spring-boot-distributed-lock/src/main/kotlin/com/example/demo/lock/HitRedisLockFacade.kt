package com.example.demo.lock

import com.example.demo.aspect.RedissonLock
import com.example.demo.hits.HitRedisRepository
import org.springframework.stereotype.Component

@Component
class HitRedisLockFacade(
    private val hitRedisRepository: HitRedisRepository,
    private val redisLockRepository: RedisLockRepository
) {
    @RedissonLock(key = "hitId")
    fun increaseRedissonLock(hitId: Long): Int {
        return hitRedisRepository.increase(hitId)
    }

    fun increaseLock(hitId: Long): Int {
        while (!redisLockRepository.lock(hitId)) {
            Thread.sleep(50)
        }

        return try {
            hitRedisRepository.increase(hitId)
        } finally {
            redisLockRepository.unlock(hitId)
        }
    }
}