package com.example.demo.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestTokenDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GuestToken {
        @JsonProperty("grant_type")
        private String grantType;

        public GuestToken(String grantType) {
            this.grantType = grantType;
        }
    }
}
