package com.example.demo.stock

import java.lang.IllegalArgumentException
import javax.persistence.*

@Entity
@Table(name = "stocks")
class Stock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "quantity")
    var quantity: Int = 0,
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