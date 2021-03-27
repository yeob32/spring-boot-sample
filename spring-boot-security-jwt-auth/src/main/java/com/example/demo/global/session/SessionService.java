package com.example.demo.global.session;

import com.example.demo.domain.user.User;
import com.example.demo.global.security.model.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public void putSession(UserContext userContext) {
        sessionRepository.putSession(userContext.getSessionKey(), userContext);
    }

    public void updateSessionUser(String sessionKey, User user) {
        UserContext userContext = sessionRepository.getSession(sessionKey);
        userContext.setUser(user);
    }

    public UserContext getSession(String key) {
        return sessionRepository.getSession(key);
    }

    public void removeSession(String key) {
        sessionRepository.removeSession(key);
    }

    public Map<String, UserContext> getSessionMap() {
        return sessionRepository.getSessionMap();
    }
}
