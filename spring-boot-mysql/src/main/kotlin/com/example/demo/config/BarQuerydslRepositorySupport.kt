package com.example.demo.config

import com.example.demo.bar.person.Person
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class BarQuerydslRepositorySupport : QuerydslRepositorySupport(Person::class.java) {
    lateinit var jpaQueryFactory: JPAQueryFactory

    @PersistenceContext(unitName = "barEntityManager")
    override fun setEntityManager(entityManager: EntityManager) {
        super.setEntityManager(entityManager)
        jpaQueryFactory = JPAQueryFactory(entityManager)
    }
}