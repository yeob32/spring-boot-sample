package com.example.demo.stock

import com.example.demo.aspect.RedissonLock
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockOptimisticLockService(
    private val stockOptimisticLockRepository: StockOptimisticLockRepository
) {
    @Transactional
    fun create(id: Long, quantity: Int): StockOptimisticLock {
        return stockOptimisticLockRepository.save(
            StockOptimisticLock(
                id = id,
                quantity = quantity,
            )
        )
    }

    @Transactional
    fun decrease(id: Long, quantity: Int): StockOptimisticLock {
        val stock = stockOptimisticLockRepository.findByIdOrNull(id) ?: StockOptimisticLock()
        stock.decrease(quantity)
        return stockOptimisticLockRepository.save(stock)
    }

    @Transactional
    fun getStock(id: Long): StockOptimisticLock {
        return stockOptimisticLockRepository.findByIdOrNull(id) ?: throw RuntimeException()
    }

    @Transactional
    fun decreaseOptimisticLock(id: Long, quantity: Int): StockOptimisticLock {
        val stock = stockOptimisticLockRepository.findByIdOrNullOptimisticLock(id) ?: StockOptimisticLock()
        stock.decrease(quantity)
        return stockOptimisticLockRepository.save(stock)
    }
}