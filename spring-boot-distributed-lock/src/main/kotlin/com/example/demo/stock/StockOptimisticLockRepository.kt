package com.example.demo.stock

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import javax.persistence.LockModeType

interface StockOptimisticLockRepository : JpaRepository<StockOptimisticLock, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query(
        """
        select s from StockOptimisticLock s where s.id = :id
    """
    )
    fun findByIdOrNullOptimisticLock(id: Long): StockOptimisticLock?
}