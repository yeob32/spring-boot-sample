package com.example.demo.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReqSignIn {
        private String email;
        private String password;

        public ReqSignIn(String id, String password) {
            this.email = id;
            this.password = password;
        }

    }
}
