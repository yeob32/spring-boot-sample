package com.example.demo.domain.auth;

import com.example.demo.global.security.model.UserContext;
import com.example.demo.global.security.jwt.token.AccessJwtToken;
import com.example.demo.global.security.jwt.token.JwtTokenFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtTokenFactory jwtTokenFactory;

    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        return jwtTokenFactory.createAccessJwtToken(userContext);
    }
}
