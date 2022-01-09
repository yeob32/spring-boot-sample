package com.example.demo

import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerAutoConfiguration
import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanFactoryUtils
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils
import java.util.*

@Configuration
class ResilienceDependsOnBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    @Throws(BeansException::class)
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
            beanFactory, EndpointAutoConfiguration::class.java, true, false
        )
        Arrays.stream(beanNames)
            .map { beanName: String -> beanFactory.getBeanDefinition(beanName) }
            .forEach { definition ->
                definition.setDependsOn(
                    *StringUtils.addStringToArray(
                        definition.dependsOn,
                        CircuitBreakerAutoConfiguration::class.java.canonicalName
                    )
                )
            }
    }
}