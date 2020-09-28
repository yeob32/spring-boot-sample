package com.example.demo.security.auth.refresh;

import com.example.demo.global.util.CookieUtil;
import com.example.demo.security.config.SecurityConfig;
import com.example.demo.security.token.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class RefreshTokenApi {

    private final RefreshTokenService refreshTokenService;

    @GetMapping(value = "/api/auth/token", produces = {MediaType.APPLICATION_JSON_VALUE})
    public JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JwtToken jwtToken = refreshTokenService.refresh(request.getHeader(SecurityConfig.AUTHENTICATION_HEADER_NAME));
        response.addCookie(CookieUtil.createCookie("session", jwtToken.getToken()));
        return jwtToken;
    }
}
