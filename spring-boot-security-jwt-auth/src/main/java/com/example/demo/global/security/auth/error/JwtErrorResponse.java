package com.example.demo.global.security.auth.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtErrorResponse {

    private int status;
    private int code;
    private String message;

    private JwtErrorResponse(int status, final JwtErrorCode errorCode, final String message) {
        this.status = status;
        this.code = errorCode.getErrorCode();
        this.message = message;
    }

    public static JwtErrorResponse of(HttpStatus status, final JwtErrorCode errorCode, final String message) {
        return new JwtErrorResponse(status.value(), errorCode, message);
    }
}
