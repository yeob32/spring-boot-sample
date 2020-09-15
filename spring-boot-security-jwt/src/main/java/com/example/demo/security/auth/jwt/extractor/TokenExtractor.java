package com.example.demo.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}