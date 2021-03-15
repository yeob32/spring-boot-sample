package com.example.demo.domain.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemorySessionService implements SessionService {

    private static final Map<Long, User> sessionMap = new HashMap<>();

    public User getSession(Long id) {
        return sessionMap.get(id);
    }

    public void putSession(User user) {
        sessionMap.put(user.getId(), user);
    }
}
