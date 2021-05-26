package com.example.demo.bar.person

import com.example.demo.config.BarQuerydslRepositorySupport
import com.example.demo.bar.person.QPerson.person
import org.springframework.stereotype.Repository

@Repository
class PersonQueryRepository: BarQuerydslRepositorySupport() {

    fun test() = jpaQueryFactory.selectFrom(person).fetch()
}