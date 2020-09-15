package com.example.demo.security.auth;

import com.example.demo.security.error.JwtErrorCode;
import com.example.demo.security.error.JwtErrorResponse;
import com.example.demo.security.exception.AuthMethodNotSupportedException;
import com.example.demo.security.exception.JwtExpiredTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AwareAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper;

    public AwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (e instanceof BadCredentialsException) {
            mapper.writeValue(response.getWriter(), JwtErrorResponse.of(HttpStatus.UNAUTHORIZED, JwtErrorCode.AUTHENTICATION, "Invalid username or password"));
        } else if (e instanceof JwtExpiredTokenException) {
            mapper.writeValue(response.getWriter(), JwtErrorResponse.of(HttpStatus.UNAUTHORIZED, JwtErrorCode.JWT_TOKEN_EXPIRED, "Token has expired"));
        } else if (e instanceof AuthMethodNotSupportedException) {
            mapper.writeValue(response.getWriter(), JwtErrorResponse.of(HttpStatus.UNAUTHORIZED, JwtErrorCode.AUTHENTICATION, e.getMessage()));
        }

        mapper.writeValue(response.getWriter(), JwtErrorResponse.of(HttpStatus.UNAUTHORIZED, JwtErrorCode.AUTHENTICATION, "Authentication failed"));
    }
}