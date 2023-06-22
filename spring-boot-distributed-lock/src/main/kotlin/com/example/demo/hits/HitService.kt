package com.example.demo.hits

import com.example.demo.aspect.RedissonLock
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HitService(
    private val hitRepository: HitRepository
) {
    @Transactional
    fun create() {
        hitRepository.save(Hit())
    }

    @Transactional
    fun increase(hitId: Long): Hit {
        val hit = hitRepository.findByIdOrNull(hitId) ?: Hit()
        hit.increase()
        return hitRepository.save(hit)
    }

    @Transactional(readOnly = true)
    fun getHit(hitId: Long): Hit {
        return hitRepository.findByIdOrNull(hitId) ?: throw RuntimeException()
    }

    @RedissonLock(key = "hitId")
    fun increaseRedissonLock(hitId: Long): Hit {
        return increase(hitId)
    }

    @Transactional
    fun increasePessimisticLock(hitId: Long): Hit {
        val hit = hitRepository.findByIdOrNullPessimisticLock(hitId) ?: Hit()
        hit.increase()
        return hitRepository.save(hit)
    }

    @Transactional
    fun increaseOptimisticLock(hitId: Long): Hit {
        val hit = hitRepository.findByIdOrNullOptimisticLock(hitId) ?: Hit()
        hit.increase()
        return hitRepository.save(hit)
    }
}