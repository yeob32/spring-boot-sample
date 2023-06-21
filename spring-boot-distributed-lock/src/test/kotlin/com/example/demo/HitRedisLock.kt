package com.example.demo

import com.example.demo.hits.HitRedisRepository
import com.example.demo.lock.HitRedisLockFacade
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class HitRedisLock {
    @Autowired
    lateinit var hitRedisLockFacade: HitRedisLockFacade
    @Autowired
    lateinit var hitRedisRepository: HitRedisRepository

    val threadPoolContext = newFixedThreadPoolContext(10, "Redis-threads")

    @Test
    fun hit_lettuce_lock_test() {
        val count = 10
        val hitId = 1L
        hitRedisRepository.reset(hitId)

        runBlocking(threadPoolContext) {
            for (i in 1..count) {
                launch {
                    hitRedisLockFacade.increaseLock(hitId)
                }
            }
        }

        val hits = hitRedisRepository.getHits(hitId)
        assertThat(hits.count).isEqualTo(count)
    }

    @Test
    fun hit_aop_lock_test() {
        val count = 1000
        val hitId = 1L
        hitRedisRepository.reset(hitId)

        runBlocking(threadPoolContext) {
            for (i in 1..count) {
                launch {
                    hitRedisLockFacade.increaseRedissonLock(hitId)
                }
            }
        }

        val counter = hitRedisRepository.getHits(hitId)
        assertThat(counter.count).isEqualTo(count)
    }
}