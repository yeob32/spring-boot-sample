package com.example.demo.global.loader;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.user.model.Email;
import com.example.demo.domain.user.model.Password;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DummyUserLoader implements ApplicationRunner {
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start create Users");

        IntStream.range(0, 10).forEach(i -> {
            String name = "test_" + i;
            User user = User.builder()
                    .name(name)
                    .email(Email.of(name + "@google.com"))
                    .password(Password.of("1234"))
                    .build();
            userRepository.save(user);
        });

        log.info("end create Users");
    }
}
