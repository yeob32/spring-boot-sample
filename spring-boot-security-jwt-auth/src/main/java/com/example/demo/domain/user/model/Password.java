package com.example.demo.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Password {

    @Column(name = "password")
    private String value;

    @Builder
    public Password(String value) {
        this.value = value;
    }

    public static Password of(String value) {
        return new Password(value);
    }
}
