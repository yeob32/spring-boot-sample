package com.example.demo.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Aspect
@Component
class RedissonLockAspect(
    private val redissonClient: RedissonClient,
    private val aspectForTransaction: AspectForTransaction
) {
    companion object {
        private const val REDISSON_LOCK_KEY = "RLOCK_"
    }

    @Around("@annotation(com.example.demo.aspect.RedissonLock)")
    @Throws(Throwable::class)
    fun lock(joinPoint: ProceedingJoinPoint): Any {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val redissonLock: RedissonLock = method.getAnnotation(RedissonLock::class.java)

        val key = REDISSON_LOCK_KEY + createKey(signature.parameterNames, joinPoint.args, redissonLock.key)

        val rLock = redissonClient.getLock(key)
        return try {
            val isPossible = rLock.tryLock(redissonLock.waitTime, redissonLock.leaseTime, redissonLock.timeUnit)
            if (!isPossible) {
                return false
            }

            aspectForTransaction.proceed(joinPoint)
        } catch (e: Exception) {
            throw InterruptedException()
        } finally {
            try {
                rLock.unlock()
            } catch (e: IllegalMonitorStateException) {
                println("Redisson Lock Already UnLock ${method.name} $key")
            }
        }
    }

    private fun createKey(parameterNames: Array<String>, args: Array<Any>, key: String): String {
        var resultKey = key

        for (i in parameterNames.indices) {
            if (parameterNames[i] == key) {
                resultKey += args[i]
                break
            }
        }
        return resultKey
    }
}