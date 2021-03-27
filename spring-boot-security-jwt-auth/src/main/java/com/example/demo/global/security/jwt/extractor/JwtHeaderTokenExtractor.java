package com.example.demo.global.security.jwt.extractor;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
@Primary
public class JwtHeaderTokenExtractor implements TokenExtractor {
    public static final String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {
        if (header == null || header.isBlank()) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return header.substring(HEADER_PREFIX.length());
    }
}