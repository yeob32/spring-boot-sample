package com.example.demo.security.exception;

import com.example.demo.security.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {

    private final JwtToken token;

    public JwtExpiredTokenException(String msg, JwtToken token) {
        super(msg);
        this.token = token;
    }

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}