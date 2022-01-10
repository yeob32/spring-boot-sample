package com.example.demo

import feign.RequestLine

interface FooFeignClient {
    @RequestLine("GET /accounts")
    fun getAccounts(): Iterable<Any>
}