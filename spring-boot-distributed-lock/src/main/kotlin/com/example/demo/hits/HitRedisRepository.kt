package com.example.demo.hits

import com.example.demo.aspect.RedissonLock
import com.example.demo.lock.RedisLockRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class HitRedisRepository(
    private val redisTemplate: RedisTemplate<String, HitRedis>,
    private val redisLockRepository: RedisLockRepository
) {
    companion object {
        const val hashKey = "HitsHash"
    }

    fun getHits(hitId: Long): HitRedis {
        val key = getKey(hitId)
        return redisTemplate.opsForHash<String, HitRedis>()[key, hashKey]
            ?: HitRedis(
                id = hitId,
                count = 0
            )
    }

    fun increase(hitId: Long): Int {
        val hash = redisTemplate.opsForHash<String, HitRedis>()
        val hits = getHits(hitId)
        val increasedCount = hits.increase()
        hash.put(getKey(hitId), hashKey, hits)
        return increasedCount
    }

    fun reset(hitId: Long) {
        val hash = redisTemplate.opsForHash<String, HitRedis>()
        val counter = getHits(hitId)
        counter.reset()
        hash.put(getKey(hitId), hashKey, counter)
    }

    private fun getKey(hitId: Long): String {
        return "hits:$hitId"
    }
}