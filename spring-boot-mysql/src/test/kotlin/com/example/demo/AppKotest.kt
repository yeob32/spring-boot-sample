package com.example.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AppKotest: FunSpec() {
    init {
        test("첫번 째 테스트") {
            val name = "ksy"
            name.shouldBe("ksy")
        }
    }
}