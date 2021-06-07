package com.example.demo.aspect

import mu.KLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Component
@Aspect
class LoggingAspect {

    companion object : KLogging()

//    @Pointcut("bean(*Controller))")
    @Pointcut("execution(public * *(..)) && (within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *))")
    fun onRequest() {
    }

    @Before("onRequest()")
    fun before(joinPoint: JoinPoint) {
        logger.info { "========== Aspect Before Start ==========" }

        MDC.clear()

        logger.info { "========== Aspect Before End ==========" }
    }

    @AfterReturning(pointcut = "onRequest()", returning = "result")
    fun afterReturning(joinPoint: JoinPoint, result: Any?) {
        logger.info { "========== Aspect afterReturning Start ==========" }

        // 헤더 식별값 통해서 추적 ex) SecurityContextHolder

        val servletRequestAttributes = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        val request = servletRequestAttributes.request

        MDC.put("trace_id", UUID.randomUUID().toString())

        logger.info { "========== Aspect afterReturning End ==========" }
    }

    @Around("com.example.demo.aspect.LoggingAspect.onRequest()")
    fun requestLogging(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()
        try {
            return proceedingJoinPoint.proceed()
        } finally {
            val end = System.currentTimeMillis()
            logger.info { "${end - start} - request logging !!!" }
        }
    }
}