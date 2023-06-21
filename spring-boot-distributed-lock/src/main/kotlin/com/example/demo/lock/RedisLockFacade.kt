package com.example.demo.lock

import com.example.demo.hits.Hit
import com.example.demo.hits.HitService
import org.springframework.stereotype.Component

@Component
class RedisLockFacade(
    private val hitService: HitService,
    private val redisLockRepository: RedisLockRepository
) {
    fun increase(hitId: Long): Hit {
        while (!redisLockRepository.lock(hitId)) {
            Thread.sleep(50)
        }

        return try {
            hitService.increase(hitId)
        } finally {
            redisLockRepository.unlock(hitId)
        }
    }
}