package com.example.demo.security.error;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JwtErrorCode {
    GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11);

    private final int errorCode;

    JwtErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
