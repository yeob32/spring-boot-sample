package com.example.demo.encoder;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void test() {

        String password = "1234";
        String encodedPassword = passwordEncoder.encode(password);

        boolean match = passwordEncoder.matches(password, encodedPassword);
        Assertions.assertThat(match).isTrue();
    }
}
