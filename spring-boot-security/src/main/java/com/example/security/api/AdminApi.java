package com.example.security.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminApi {

    @GetMapping("index")
    public String index(){
        return "hello Admin";
    }
}
