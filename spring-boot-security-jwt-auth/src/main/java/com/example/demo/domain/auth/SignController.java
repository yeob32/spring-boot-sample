package com.example.demo.domain.auth;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import com.example.demo.domain.auth.dto.ResponseTokenDto;
import com.example.demo.domain.auth.dto.SignDto;
import com.example.demo.global.security.model.UserContext;
import com.example.demo.global.security.jwt.token.AccessJwtToken;
import com.example.demo.global.security.utils.UserContextUtil;
import com.example.demo.global.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.global.config.WebSecurityConfig.SIGN_IN_URL;
import static com.example.demo.global.config.WebSecurityConfig.SIGN_OUT_URL;

@RequiredArgsConstructor
@RestController
public class SignController {

    private final AuthService authService;
    private final UserService userService;
    private final SessionService sessionService;

    @PostMapping(SIGN_IN_URL)
    public ResponseEntity<ResponseTokenDto> signIn(@RequestBody SignDto.ReqSignIn dto) {
        User user = userService.getUserBySignIn(dto.getEmail(), dto.getPassword());

        UserContext userContext = UserContext.createAccessUserContext(user);
        AccessJwtToken authToken = authService.createAccessJwtToken(userContext);
        sessionService.putSession(userContext);

        return ResponseEntity.ok(ResponseTokenDto.of(authToken));
    }

    @PostMapping(SIGN_OUT_URL)
    public ResponseEntity<Void> signOut() {
        sessionService.removeSession(UserContextUtil.getSessionKey());
        return ResponseEntity.ok().build();
    }
}
