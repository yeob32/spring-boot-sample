package com.example.demo.api;

import com.example.demo.user.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/public")
@RestController
public class SimpleApi {

    private final UserRepository userRepository;

    @GetMapping("/menu1")
    public String test1(){
        return "API Menu 1";
    }

    @GetMapping("/menu2")
    public String test2(){
        return "API Menu 2";
    }

    @GetMapping("/users")
    public List<User> allUsers(){
        return this.userRepository.findAll();
    }
}
