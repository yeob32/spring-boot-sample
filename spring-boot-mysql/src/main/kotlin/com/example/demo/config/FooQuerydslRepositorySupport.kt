package com.example.demo.config

import com.example.demo.bar.person.Person
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class FooQuerydslRepositorySupport : QuerydslRepositorySupport(Person::class.java) {
    lateinit var jpaQueryFactory: JPAQueryFactory

    @PersistenceContext(unitName = "footEntityManager")
    override fun setEntityManager(entityManager: EntityManager) {
        super.setEntityManager(entityManager)
        jpaQueryFactory = JPAQueryFactory(entityManager)
    }
}