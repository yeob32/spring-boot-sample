package com.example.mockito.user.repository;

import com.example.mockito.user.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DefaultUserRepository implements UserRepository {

    public static final Map<Long, User> userMap = new HashMap<>();

    @Override
    public User findUser(long id) {
        return userMap.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public long count() {
        return userMap.size();
    }
}
