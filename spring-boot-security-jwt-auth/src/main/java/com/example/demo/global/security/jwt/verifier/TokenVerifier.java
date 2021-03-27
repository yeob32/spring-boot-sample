package com.example.demo.global.security.jwt.verifier;

public interface TokenVerifier {
    boolean verify(String jti);
}
