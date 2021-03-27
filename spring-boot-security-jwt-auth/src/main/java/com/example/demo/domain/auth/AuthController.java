package com.example.demo.domain.auth;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import com.example.demo.domain.user.dto.RequestUserDto;
import com.example.demo.domain.user.dto.ResponseUserDto;
import com.example.demo.global.security.jwt.JwtProperties;
import com.example.demo.domain.auth.dto.RequestTokenDto;
import com.example.demo.domain.auth.dto.ResponseTokenDto;
import com.example.demo.global.security.jwt.extractor.TokenExtractor;
import com.example.demo.global.security.model.UserContext;
import com.example.demo.global.security.jwt.token.AccessJwtToken;
import com.example.demo.global.security.jwt.token.JwtTokenFactory;
import com.example.demo.global.security.utils.UserContextUtil;
import com.example.demo.global.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.global.config.WebSecurityConfig.AUTHENTICATION_HEADER_NAME;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final SessionService sessionService;
    private final JwtProperties jwtProperties;
    private final JwtTokenFactory jwtTokenFactory;

    @Autowired
    @Qualifier("basicHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    @PostMapping("/auth/token")
    public ResponseEntity<ResponseTokenDto> ingress(@RequestHeader(AUTHENTICATION_HEADER_NAME) String authorization,
                                                    @RequestBody RequestTokenDto.GuestToken dto) {
        String payload = tokenExtractor.extract(authorization);
        if (!StringUtils.equals(payload, jwtProperties.getIngressSigningKey())) {
            throw new AuthenticationServiceException("not authorized");
        }

        if (StringUtils.isEmpty(dto.getGrantType())) {
            throw new AuthenticationServiceException("not support credential");
        }

        UserContext userContext = UserContext.createDefaultUserContext(payload);
        AccessJwtToken guestToken = jwtTokenFactory.createAccessJwtToken(userContext);
        sessionService.putSession(userContext);

        return ResponseEntity.ok().body(ResponseTokenDto.of(guestToken));
    }

    @GetMapping(value = "/auth/v1/me")
    public ResponseEntity<ResponseUserDto.ResMe> needAuthTokenApi() {
        UserContext userContext = sessionService.getSession(UserContextUtil.getSessionKey());
        return ResponseEntity.ok(ResponseUserDto.ResMe.of(userContext.getUser()));
    }

    @PutMapping(value = "/auth/v1/me")
    public ResponseEntity<ResponseUserDto.ResMe> updateUser(@RequestBody RequestUserDto.ReqUpdate dto) {
        UserContext userContext = sessionService.getSession(UserContextUtil.getSessionKey());
        User user = userContext.getUser();
        return ResponseEntity.ok(ResponseUserDto.ResMe.of(userService.updateUser(user.getId(), dto)));
    }

    @PostMapping(value = "/auth/v1/user")
    public ResponseEntity<User> createUser() {
        return ResponseEntity.ok(userService.createUser());
    }
}