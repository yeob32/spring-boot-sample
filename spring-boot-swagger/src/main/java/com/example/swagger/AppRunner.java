package com.example.swagger;

import com.example.swagger.domain.user.User;
import com.example.swagger.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.createUser("huh", "yeob32@gmail.com");

        userRepository.save(user);
    }
}
