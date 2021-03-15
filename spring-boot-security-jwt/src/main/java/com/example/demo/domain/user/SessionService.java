package com.example.demo.domain.user;

public interface SessionService {

    User getSession(Long id);
    void putSession(User user);
}
