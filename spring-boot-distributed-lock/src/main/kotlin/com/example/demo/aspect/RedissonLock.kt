package com.example.demo.aspect

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedissonLock(
    val key: String,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
    val waitTime: Long = 10L, // lock 획득 요청 시간, 초과 시 false 반환
    val leaseTime: Long = 3L // lock 점유 시간, 초과 시 자동으로 lock 해제
)