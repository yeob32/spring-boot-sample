package com.example.demo.global.session;

import com.example.demo.global.security.model.UserContext;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySessionRepository implements SessionRepository {

    private static final Map<String, UserContext> sessionMap = new ConcurrentHashMap<>(100);

    @Override
    public void putSession(String key, UserContext value) {
        sessionMap.put(key, value);
    }

    @Override
    public UserContext getSession(String key) {
        return sessionMap.get(key);
    }

    @Override
    public void removeSession(String key) {
        sessionMap.remove(key);
    }

    @Override
    public Map<String, UserContext> getSessionMap() {
        return sessionMap;
    }

}
