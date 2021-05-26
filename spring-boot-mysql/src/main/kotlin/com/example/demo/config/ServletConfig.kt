package com.example.demo.config

import org.apache.catalina.connector.Connector
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServletConfig {

    @Bean
    fun serverFactory() = TomcatServletWebServerFactory().apply {
        addAdditionalTomcatConnectors(createStandardConnector())
    }

    private fun createStandardConnector() = Connector("org.apache.coyote.http11.Http11NioProtocol")
        .apply {
            port = 8081
        }
}