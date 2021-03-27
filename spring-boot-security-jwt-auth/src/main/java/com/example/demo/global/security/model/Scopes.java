package com.example.demo.global.security.model;

public enum Scopes {
    USER,
    ANONYMOUS,
    REFRESH_TOKEN;

    public String authority() {
        return "ROLE_" + this.name();
    }
}