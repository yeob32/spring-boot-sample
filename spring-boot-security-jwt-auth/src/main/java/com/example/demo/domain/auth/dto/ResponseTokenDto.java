package com.example.demo.domain.auth.dto;

import com.example.demo.global.security.jwt.token.AccessJwtToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseTokenDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String scope;

    @Builder
    public ResponseTokenDto(String accessToken, String tokenType, int expiresIn, String refreshToken, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    public static ResponseTokenDto of(AccessJwtToken accessJwtToken) {
        return ResponseTokenDto.builder()
                .accessToken(accessJwtToken.getToken())
                .tokenType("Bearer")
                .scope(String.valueOf(accessJwtToken.getClaims().get("scope")))
                .expiresIn(10800)
                .build();
    }
}
