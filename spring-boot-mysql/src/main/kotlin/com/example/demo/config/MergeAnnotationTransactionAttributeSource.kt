package com.example.demo.config

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
import org.springframework.transaction.interceptor.DefaultTransactionAttribute
import org.springframework.transaction.interceptor.TransactionAttribute
import java.lang.reflect.Method

class MergeAnnotationTransactionAttributeSource : AnnotationTransactionAttributeSource() {

    override fun computeTransactionAttribute(method: Method, targetClass: Class<*>?): TransactionAttribute? {
        return super.computeTransactionAttribute(method, targetClass)?.apply {
            targetClass?.let {
                if (this is DefaultTransactionAttribute && it.isAnnotationPresent(Service::class.java) && qualifier?.isEmpty() == true) {
                    if (targetClass.packageName.startsWith("com.example.demo.bar")) {
                        qualifier = BAR_TRANSACTION_MANAGER
                    }

                    if (targetClass.packageName.startsWith("com.example.demo.foo")) {
                        qualifier = FOO_TRANSACTION_MANAGER
                    }
                }
            }
        }
    }
}