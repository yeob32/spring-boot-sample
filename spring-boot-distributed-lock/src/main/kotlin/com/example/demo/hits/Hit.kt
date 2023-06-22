package com.example.demo.hits

import javax.persistence.*

@Entity
@Table(name = "hits")
class Hit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "count")
    var count: Int = 0,

    @Version
    var version: Long = 0
) {
    fun increase(): Int {
        count += 1
        return count
    }

    fun reset() {
        count = 0
    }
}