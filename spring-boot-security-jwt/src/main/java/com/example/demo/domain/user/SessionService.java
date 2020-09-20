package com.example.demo.domain.user;

import com.example.demo.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionService {

    private static final Map<Long, User> sessionMap = new HashMap<>();

    public User getSession(Long id) {
        return sessionMap.get(id);
    }

    public void putSession(Long id, User user) {
        sessionMap.put(id, user);
    }
}
