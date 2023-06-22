package com.example.demo.stock

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import javax.persistence.LockModeType

interface StockRepository : JpaRepository<Stock, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
        """
        select s from Stock s where s.id = :id
    """
    )
    fun findByIdOrNullPessimisticLock(id: Long): Stock?
}