package com.example.swagger.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class DefaultApi {

    @PostMapping("/default")
    public String test1() {
        return "test1";
    }

    @GetMapping("/default")
    public String test2() {
        return "test2";
    }
}
