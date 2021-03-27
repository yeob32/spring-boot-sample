package com.example.demo.domain.user.dto;

import com.example.demo.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUserDto {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class SignUpRes {

        private Long id;

        @Builder
        public SignUpRes(Long id) {
            this.id = id;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class SocialSignUpRes {

        private Long id;

        @Builder
        public SocialSignUpRes(Long id) {
            this.id = id;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Users {

        private Long id;
        private String name;
        private String email;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private User createBy;
        private User updatedBy;

        @Builder
        public Users(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail().getValue();
            this.createAt = user.getCreatedAt();
            this.createBy = user.getCreatedBy();
            this.updatedBy = user.getUpdatedBy();
            this.updateAt = user.getUpdatedAt();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ResMe {

        private Long id;
        private String name;
        private String email;

        @Builder
        public ResMe(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public static ResMe of(User user) {
            return ResMe.builder()
                    .id(user.getId())
                    .email(user.getEmail().getValue())
                    .name(user.getName())
                    .build();
        }
    }
}
