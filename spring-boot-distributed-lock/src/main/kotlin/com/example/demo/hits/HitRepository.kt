package com.example.demo.hits

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import javax.persistence.LockModeType

interface HitRepository : JpaRepository<Hit, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select h from Hit h where h.id = :id
    """)
    fun findByIdOrNullPessimisticLock(id: Long): Hit?

    @Lock(LockModeType.OPTIMISTIC)
    @Query("""
        select h from Hit h where h.id = :id
    """)
    fun findByIdOrNullOptimisticLock(id: Long): Hit?
}