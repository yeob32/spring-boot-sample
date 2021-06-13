package com.example.demo.config

import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor
import org.springframework.stereotype.Component
import org.springframework.transaction.interceptor.TransactionAttribute

@Component
class AnnotationTransactionAttributeSourceReplacer : InstantiationAwareBeanPostProcessor {

    companion object {
        const val BEAN_NAME = "transactionAttributeSource"
    }

    override fun postProcessBeforeInstantiation(beanClass: Class<*>, beanName: String): Any? {
        if (beanName == BEAN_NAME && TransactionAttribute::class.java.isAssignableFrom(beanClass)) {
            return MergeAnnotationTransactionAttributeSource()
        }

        return null
    }

    fun getOrder(): Int {
        return 0
    }
}