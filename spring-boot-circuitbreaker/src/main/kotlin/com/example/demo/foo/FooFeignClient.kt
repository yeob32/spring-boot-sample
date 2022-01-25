package com.example.demo.foo

import feign.RequestLine

interface FooFeignClient {
    @RequestLine("GET /accounts")
    fun getAccounts(): Iterable<Any>
}