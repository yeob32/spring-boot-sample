package com.example.demo.config;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class Company {

    private String name;

    public Company(String name) {
        this.name = name;
    }
}
