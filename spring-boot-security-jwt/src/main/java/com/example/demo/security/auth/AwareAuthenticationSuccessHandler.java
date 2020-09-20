package com.example.demo.security.auth;

import com.example.demo.domain.user.SessionService;
import com.example.demo.global.util.CookieUtil;
import com.example.demo.security.model.UserContext;
import com.example.demo.security.token.JwtToken;
import com.example.demo.security.token.JwtTokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;
    private final SessionService sessionService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserContext userContext = (UserContext) authentication.getPrincipal();

        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);

//        sessionService.putSession(accessToken.getToken(), );

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addCookie(CookieUtil.createCookie("session", accessToken.getToken()));

        mapper.writeValue(response.getWriter(), result);

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}