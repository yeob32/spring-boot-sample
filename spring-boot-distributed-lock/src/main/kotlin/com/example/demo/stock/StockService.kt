package com.example.demo.stock

import com.example.demo.aspect.RedissonLock
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository
) {
    @Transactional
    fun create(id: Long, quantity: Int): Stock {
        return stockRepository.save(
            Stock(
                id = id,
                quantity = quantity,
            )
        )
    }

    @Transactional
    fun decrease(id: Long, quantity: Int): Stock {
        val stock = stockRepository.findByIdOrNull(id) ?: Stock()
        stock.decrease(quantity)
        return stockRepository.save(stock)
    }

    @Transactional
    fun getStock(id: Long): Stock {
        return stockRepository.findByIdOrNull(id) ?: throw RuntimeException()
    }

    @RedissonLock(key = "id")
    fun decreaseRedissonLock(id: Long, quantity: Int): Stock {
        return decrease(id, quantity)
    }

    @Transactional
    fun decreasePessimisticLock(id: Long, quantity: Int): Stock {
        val stock = stockRepository.findByIdOrNullPessimisticLock(id) ?: Stock()
        stock.decrease(quantity)
        return stockRepository.save(stock)
    }
}