package com.example.demo.security.auth.jwt.verifier;

public interface TokenVerifier {
    boolean verify(String jti);
}
