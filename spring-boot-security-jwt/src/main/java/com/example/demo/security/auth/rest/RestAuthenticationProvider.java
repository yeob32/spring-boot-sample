package com.example.demo.security.auth.rest;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import com.example.demo.security.model.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder encoder;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userService.getByEmail(email);

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        UserContext userContext = UserContext.create(user.getEmail(), new ArrayList<>());

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
