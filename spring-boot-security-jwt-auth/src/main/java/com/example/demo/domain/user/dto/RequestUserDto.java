package com.example.demo.domain.user.dto;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.model.Email;
import com.example.demo.domain.user.model.Password;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUserDto {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class SignUp {
        private String email;
        private String password;

        @Builder
        public SignUp(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public User toEntity() {
            return User.builder()
                    .email(Email.of(email))
                    .password(Password.of(password))
                    .build();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ReqUpdate {
        private String name;

        @Builder
        public ReqUpdate(String name) {
            this.name = name;
        }
    }
}
