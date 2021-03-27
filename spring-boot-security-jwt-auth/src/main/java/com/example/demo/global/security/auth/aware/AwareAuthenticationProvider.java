package com.example.demo.global.security.auth.aware;

import com.example.demo.global.security.jwt.JwtProperties;
import com.example.demo.global.session.SessionService;
import com.example.demo.global.security.model.UserContext;
import com.example.demo.global.security.jwt.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AwareAuthenticationProvider implements AuthenticationProvider {

    private final JwtProperties jwtProperties;
    private final SessionService sessionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtProperties.getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject();

        UserContext userContext = sessionService.getSession(subject);
        if(userContext == null || !userContext.isSigned()) {
            throw new AuthenticationServiceException("401 Not Authorized");
        }

        return new AwareAuthenticationToken(userContext, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (AwareAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
