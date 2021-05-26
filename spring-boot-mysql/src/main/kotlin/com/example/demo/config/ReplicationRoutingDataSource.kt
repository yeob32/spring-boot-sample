package com.example.demo.config

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class ReplicationRoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any =
        when {
            TransactionSynchronizationManager.isCurrentTransactionReadOnly() -> "slave"
            else -> "master"
        }
}