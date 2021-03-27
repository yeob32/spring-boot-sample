package com.example.demo.global.session;

import com.example.demo.global.security.model.UserContext;

import java.util.Map;

public interface SessionRepository {
    void putSession(String key, UserContext value);
    UserContext getSession(String key);
    void removeSession(String key);
    Map<String, UserContext> getSessionMap();
}
