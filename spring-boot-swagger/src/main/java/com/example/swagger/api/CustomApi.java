package com.example.swagger.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomApi {

    @PostMapping(value = "/custom")
    public String custom() {
        return "custom";
    }
}
