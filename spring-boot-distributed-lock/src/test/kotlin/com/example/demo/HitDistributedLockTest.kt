package com.example.demo

import com.example.demo.hits.HitService
import com.example.demo.lock.RedisLockFacade
import com.example.demo.lock.RedissonLockFacade
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class HitDistributedLockTest {
    @Autowired
    lateinit var hitService: HitService
    @Autowired
    lateinit var redisLockFacade: RedisLockFacade
    @Autowired
    lateinit var redissonLockFacade: RedissonLockFacade

    val threadPoolContext = newFixedThreadPoolContext(10, "Redis-threads")

    @Test
    fun `조회수 증가 동시성 테스트`() {
        val hitId = 1L

        runBlocking(threadPoolContext) {
            for(i in 1..1000) {
                launch {
                    hitService.increase(hitId)
                }
            }
        }

        val hit = hitService.getHit(hitId)
        assertThat(hit.count).isLessThan(1000)
    }

    @Test
    fun `레디스 동시성 제어`() {
        val hitId = 1L

        runBlocking(threadPoolContext) {
            for(i in 1..5) {
                launch {
                    redisLockFacade.increase(hitId)
                }
            }
        }

        val hit = hitService.getHit(hitId)
        assertThat(hit.count).isEqualTo(5)
    }

    @Test
    fun `Redisson 동시성 제어`() {
        val hitId = 1L

        runBlocking(threadPoolContext) {
            for(i in 1..1000) {
                launch {
                    redissonLockFacade.increase(hitId)
                }
            }
        }

        val hit = hitService.getHit(hitId)
        assertThat(hit.count).isEqualTo(1000)
    }

    @Test
    fun `Redisson AOP 활용하여 동시성 제어`() {
        val hitId = 1L

        runBlocking(threadPoolContext) {
            for(i in 1..1000) {
                launch {
                    hitService.increaseRedissonLock(hitId)
                }
            }
        }

        val hit = hitService.getHit(hitId)
        assertThat(hit.count).isEqualTo(1000)
    }
}