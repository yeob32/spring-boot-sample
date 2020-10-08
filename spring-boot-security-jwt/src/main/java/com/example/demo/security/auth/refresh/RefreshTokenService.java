package com.example.demo.security.auth.refresh;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import com.example.demo.security.auth.jwt.extractor.TokenExtractor;
import com.example.demo.security.auth.jwt.verifier.TokenVerifier;
import com.example.demo.security.config.JwtConfig;
import com.example.demo.security.exception.InvalidJwtToken;
import com.example.demo.security.model.UserContext;
import com.example.demo.security.token.JwtToken;
import com.example.demo.security.token.JwtTokenFactory;
import com.example.demo.security.token.RawAccessJwtToken;
import com.example.demo.security.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final JwtTokenFactory tokenFactory;
    private final JwtConfig jwtConfig;
    private final UserService userService;
    private final TokenVerifier tokenVerifier;
    private final TokenExtractor tokenExtractor;

    public JwtToken refresh(String authenticationPayload) {
        String tokenPayload = tokenExtractor.extract(authenticationPayload);

        String encodedString = Base64.getEncoder()
                .encodeToString(jwtConfig.getTokenSigningKey().getBytes());

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, encodedString)
                .orElseThrow(InvalidJwtToken::new);

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.getByEmail(subject);

//        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
//                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getEmail(), new ArrayList<>());

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
