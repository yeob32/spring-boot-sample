package com.example.demo.domain.user;

import com.example.demo.domain.user.UserService;
import com.example.demo.domain.user.dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<ResponseUserDto.Users>> getUsers() {
        return ResponseEntity.ok(userService.getUsers()
                .stream()
                .map(user -> ResponseUserDto.Users
                        .builder()
                        .user(user)
                        .build())
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
