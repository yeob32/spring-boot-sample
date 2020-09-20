package com.example.demo.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "demo.security.jwt")
public class JwtConfig {
    /**
     * will expire after this time.
     */
    private Integer tokenExpirationTime;

    /**
     * Token issuer.
     */
    private String tokenIssuer;

    /**
     * Key is used to sign.
     */
    private String tokenSigningKey;

    /**
     * can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpTime;
}
