package com.example.demo.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

const val FOO_MASTER_DATASOURCE = "fooMasterDataSource"
const val FOO_SLAVE_DATASOURCE = "fooSlaveDataSource"

const val FOO_TRANSACTION_MANAGER = "fooTransactionManager"

@EnableJpaRepositories(
    basePackages = ["com.example.demo.foo"],
    entityManagerFactoryRef = "fooEntityManagerFactory",
    transactionManagerRef = FOO_TRANSACTION_MANAGER
)
@EnableTransactionManagement
@Configuration
class FooDatabaseConfiguration(private val jpaProperties: JpaProperties) {

    @Bean(name = [FOO_MASTER_DATASOURCE])
    @ConfigurationProperties(prefix = "spring.datasource.foo-master.hikari")
    fun masterDataSource(): HikariDataSource = DataSourceBuilder
        .create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = [FOO_SLAVE_DATASOURCE])
    @ConfigurationProperties("spring.datasource.foo-slave.hikari")
    fun slaveDataSource(): HikariDataSource =
        DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
            .apply { this.isReadOnly = true }

    @Bean(name = ["fooRoutingDataSource"])
    @DependsOn(FOO_MASTER_DATASOURCE, FOO_SLAVE_DATASOURCE)
    fun routingDataSource() = ReplicationRoutingDataSource().apply {
        setDefaultTargetDataSource(masterDataSource())
        setTargetDataSources(
            hashMapOf<Any, Any>(
                "master" to masterDataSource(),
                "slave" to slaveDataSource()
            )
        )
    }

    @Bean(name = ["fooDataSource"])
    @DependsOn("fooRoutingDataSource") // routingDataSource 빈 등록 후 해당 빈 등록
    fun dataSource() =
        LazyConnectionDataSourceProxy(routingDataSource())

    @Bean(name = ["fooEntityManagerFactory"])
    fun fooEntityManagerFactory() = LocalContainerEntityManagerFactoryBean().apply {
        dataSource = dataSource()
        persistenceUnitName = "fooEntityManager"
        setPackagesToScan("com.example.demo.foo")
        jpaVendorAdapter = HibernateJpaVendorAdapter().apply {
            setJpaPropertyMap(jpaProperties.properties)
            setGenerateDdl(jpaProperties.isShowSql)
            setShowSql(jpaProperties.isShowSql)
        }
        afterPropertiesSet()
    }

    @Bean(name = [FOO_TRANSACTION_MANAGER])
    fun fooTransactionManager(): PlatformTransactionManager {
        return JpaTransactionManager(fooEntityManagerFactory().`object`!!)
    }
}