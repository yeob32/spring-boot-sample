package com.example.demo.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getByEmail(String name) {
        return userRepository.findByEmail(name).orElseThrow(() -> new RuntimeException("User not found: " + name));
    }
}
