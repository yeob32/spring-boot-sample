package com.example.demo;

import com.example.demo.user.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DummyRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Delete all
        this.userRepository.deleteAll();

        // create users
        User yeob = new User("yeob", passwordEncoder.encode("1234"), "USER", "");
        User admin = new User("admin", passwordEncoder.encode("1234"), "ADMIN", "ACCESS_MENU1,ACCESS_MENU2");
        User manager = new User("manager", passwordEncoder.encode("1234"), "MANAGER", "ACCESS_MENU1");

        List<User> users = Arrays.asList(yeob, admin, manager);

        // save to db
        this.userRepository.saveAll(users);
    }
}