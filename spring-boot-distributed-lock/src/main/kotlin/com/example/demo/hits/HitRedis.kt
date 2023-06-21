package com.example.demo.hits

data class HitRedis(
    var id: Long,
    var count: Int
) {
    fun increase(): Int {
        count += 1
        return count
    }

    fun reset() {
        count = 0
    }
}