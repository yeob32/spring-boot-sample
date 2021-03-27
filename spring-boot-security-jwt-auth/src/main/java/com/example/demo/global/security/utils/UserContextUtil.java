package com.example.demo.global.security.utils;

import com.example.demo.global.security.model.UserContext;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@UtilityClass
public class UserContextUtil {

    public static UserContext getUserContext() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(authentication -> (UserContext) authentication.getPrincipal())
                .orElseThrow(() -> new RuntimeException("401 Not Authorized"));
    }

    public static String getSessionKey() {
        return getUserContext().getSessionKey();
    }

    public static String getContextId() {
        return getUserContext().getContextId();
    }
}
