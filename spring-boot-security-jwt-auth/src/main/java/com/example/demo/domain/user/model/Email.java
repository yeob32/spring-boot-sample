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
public class Email {

    @javax.validation.constraints.Email
    @Column(name = "email", unique = true, nullable = false)
    private String value;

    @Builder
    public Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String getHost() {
        int index = value.indexOf("@");
        return value.substring(index);
    }

    public String getId() {
        int index = value.indexOf("@");
        return value.substring(0, index);
    }
}
