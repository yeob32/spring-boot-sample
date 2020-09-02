package com.example.mockito.user.repository;

import com.example.mockito.user.User;

import java.util.List;

public interface UserRepository {
    User findUser(long id);
    List<User> findAll();
    long count();
}
