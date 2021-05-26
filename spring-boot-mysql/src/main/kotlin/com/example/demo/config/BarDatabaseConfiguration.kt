package com.example.demo.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

const val BAR_MASTER_DATASOURCE = "masterDataSource"
const val BAR_SLAVE_DATASOURCE = "slaveDataSource"

@EnableJpaRepositories(
    basePackages = ["com.example.demo.bar"],
    entityManagerFactoryRef = "barEntityManagerFactory",
    transactionManagerRef = "barTransactionManager"
)
@EnableTransactionManagement
@Configuration
class DatabaseConfiguration(private val jpaProperties: JpaProperties) {

    @Bean(name = [BAR_MASTER_DATASOURCE])
    @ConfigurationProperties(prefix = "spring.datasource.bar-master.hikari")
    fun masterDataSource(): HikariDataSource = DataSourceBuilder
        .create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = [BAR_SLAVE_DATASOURCE])
    @ConfigurationProperties("spring.datasource.bar-slave.hikari")
    fun slaveDataSource(): HikariDataSource =
        DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
            .apply { this.isReadOnly = true }

    @Bean(name = ["barRoutingDataSource"])
    @DependsOn(BAR_MASTER_DATASOURCE, BAR_SLAVE_DATASOURCE)
    fun routingDataSource() = ReplicationRoutingDataSource().apply {
        setDefaultTargetDataSource(masterDataSource())
        setTargetDataSources(
            hashMapOf<Any, Any>(
                "master" to masterDataSource(),
                "slave" to slaveDataSource()
            )
        )
    }

    @Bean(name = ["barDataSource"])
    @Primary
    @DependsOn("barRoutingDataSource")
    fun dataSource() =
        LazyConnectionDataSourceProxy(routingDataSource())

    @Bean(name = ["barEntityManagerFactory"])
    @Primary
    fun barEntityManagerFactory() = LocalContainerEntityManagerFactoryBean().apply {
        dataSource = dataSource()
        persistenceUnitName = "barEntityManager"
        setPackagesToScan("com.example.demo.bar")
        jpaVendorAdapter = HibernateJpaVendorAdapter().apply {
            setJpaPropertyMap(jpaProperties.properties)
            setGenerateDdl(jpaProperties.isShowSql)
            setShowSql(jpaProperties.isShowSql)
        }
        afterPropertiesSet()
    }

    @Bean(name = ["barTransactionManager"])
    @Primary
    fun barTransactionManager(): PlatformTransactionManager {
        return JpaTransactionManager(barEntityManagerFactory().`object`!!)
    }
}