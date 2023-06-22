package com.example.demo

import com.example.demo.stock.StockOptimisticLockService
import com.example.demo.stock.StockService
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.ObjectOptimisticLockingFailureException
import java.lang.IllegalArgumentException

@SpringBootTest
class StockDistributedLockTest {
    @Autowired
    lateinit var stockService: StockService
    @Autowired
    lateinit var stockOptimisticLockService: StockOptimisticLockService

    val threadPoolContext = newFixedThreadPoolContext(10, "Distributed-lock-threads")

    val id = 1L
    val quantity = 10

    @BeforeEach
    fun setUp() {
        stockService.create(id, quantity)
        stockOptimisticLockService.create(id, quantity)
    }

    @Test
    fun `재고 차감 테스트`() {
        assertThrows<IllegalArgumentException> {
            for (i in 1..1000) {
                stockService.decrease(id, 1)
            }
        }

        val stock = stockService.getStock(id)
        assertThat(stock.quantity).isEqualTo(0)
    }

    @Test
    fun `재고 차감 동시성 테스트`() {
        assertThrows<IllegalArgumentException> {
            runBlocking(threadPoolContext) {
                for (i in 1..100) {
                    launch {
                        stockService.decrease(id, 1)
                    }
                }
            }
        }

        val stock = stockService.getStock(id)
        println("stock : ${stock.quantity}")
        assertThat(stock.quantity).isGreaterThan(0)
    }

    @Nested
    inner class DBLock {
        @Test
        fun `비관적 Lock`() {
            assertThrows<IllegalArgumentException> {
                runBlocking(threadPoolContext) {
                    for (i in 1..100) {
                        launch {
                            stockService.decreasePessimisticLock(id, 1)
                        }
                    }
                }
            }

            val stock = stockService.getStock(id)
            assertThat(stock.quantity).isEqualTo(0)
        }

        @Test
        fun `낙관적 Lock`() {
            assertThrows<IllegalArgumentException> {
                runBlocking(threadPoolContext) {
                    for (i in 1..100) {
                        launch {
                            try {
                                stockOptimisticLockService.decreaseOptimisticLock(id, 1)
                            } catch (e: ObjectOptimisticLockingFailureException) {
                                println("e : $e")
                            }
                        }
                    }
                }
            }

            val stock = stockOptimisticLockService.getStock(id)
            assertThat(stock.version).isEqualTo(quantity.toLong())
            assertThat(stock.quantity).isEqualTo(0)
        }
    }
}