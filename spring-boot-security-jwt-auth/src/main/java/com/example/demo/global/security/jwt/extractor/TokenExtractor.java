package com.example.demo.global.security.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
