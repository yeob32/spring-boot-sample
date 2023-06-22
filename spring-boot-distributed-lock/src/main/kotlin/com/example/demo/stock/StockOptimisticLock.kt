package com.example.demo.stock

import javax.persistence.*

@Entity
@Table(name = "stock_optimistic_lock")
class StockOptimisticLock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "quantity")
    var quantity: Int = 0,

    @Version
    var version: Long = 0
) {
    fun decrease(orderQuantity: Int) {
        validateAvailableStock(orderQuantity)
        quantity -= orderQuantity
    }

    fun validateAvailableStock(orderQuantity: Int) {
        if (quantity < orderQuantity) {
            throw IllegalArgumentException()
        }
    }
}