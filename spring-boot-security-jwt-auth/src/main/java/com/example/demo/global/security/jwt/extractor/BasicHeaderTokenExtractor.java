package com.example.demo.global.security.jwt.extractor;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
public class BasicHeaderTokenExtractor implements TokenExtractor {
    public static final String HEADER_PREFIX = "Basic ";

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