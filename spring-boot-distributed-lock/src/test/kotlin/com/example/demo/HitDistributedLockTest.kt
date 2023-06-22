package com.example.demo

import com.example.demo.hits.HitService
import com.example.demo.lock.RedisLockFacade
import com.example.demo.lock.RedissonLockFacade
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.ObjectOptimisticLockingFailureException

@SpringBootTest
class HitDistributedLockTest {
    @Autowired
    lateinit var hitService: HitService

    @Autowired
    lateinit var redisLockFacade: RedisLockFacade

    @Autowired
    lateinit var redissonLockFacade: RedissonLockFacade

    val threadPoolContext = newFixedThreadPoolContext(10, "Distributed-lock-threads")

    val hitId = 1L

    @BeforeEach
    fun setUp() {
        hitService.create()
    }

    @Test
    fun `조회수 증가 동시성 테스트`() {
        runBlocking(threadPoolContext) {
            for (i in 1..1000) {
                launch {
                    hitService.increase(hitId)
                }
            }
        }

        val hit = hitService.getHit(hitId)
        assertThat(hit.count).isLessThan(1000)
    }

    @Nested
    inner class DBLock {
        @Test
        fun `비관적 Lock`() {
            runBlocking(threadPoolContext) {
                for (i in 1..1000) {
                    launch {
                        hitService.increasePessimisticLock(hitId)
                    }
                }
            }

            val hit = hitService.getHit(hitId)
            assertThat(hit.count).isEqualTo(1000)
        }

        @Test
        fun `낙관적 Lock`() {
            runBlocking(threadPoolContext) {
                for (i in 1..1000) {
                    launch {
                        increaseOptimisticLock(hitId)
                    }
                }
            }

            val hit = hitService.getHit(hitId)
            assertThat(hit.version).isEqualTo(1000)
            assertThat(hit.count).isEqualTo(1000)
        }

        private fun increaseOptimisticLock(hitId: Long) {
            try {
                hitService.increaseOptimisticLock(hitId)
            } catch (e: ObjectOptimisticLockingFailureException) {
                increaseOptimisticLock(hitId)
            } catch (e: Exception) {
                println("e : $e")
            }
        }
    }

    @Nested
    inner class RedisLock {
        @Test
        fun `레디스 동시성 제어`() {
            runBlocking(threadPoolContext) {
                for (i in 1..5) {
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
            runBlocking(threadPoolContext) {
                for (i in 1..1000) {
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
            runBlocking(threadPoolContext) {
                for (i in 1..1000) {
                    launch {
                        hitService.increaseRedissonLock(hitId)
                    }
                }
            }

            val hit = hitService.getHit(hitId)
            assertThat(hit.count).isEqualTo(1000)
        }
    }
}