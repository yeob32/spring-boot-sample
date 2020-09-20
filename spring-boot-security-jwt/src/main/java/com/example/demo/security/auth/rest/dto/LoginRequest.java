package com.example.demo.security.auth.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
    private String platform;

    @JsonCreator
    public LoginRequest(@JsonProperty("email") String email,
                        @JsonProperty("password") String password,
                        @JsonProperty("platform") String platform) {
        this.email = email;
        this.password = password;
        this.platform = platform;
    }
}
