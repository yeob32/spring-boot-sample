package com.example.swagger.domain.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;

    public static User createUser(String name, String email) {
        User user = new User();
        user.name = name;
        user.email = email;

        return user;
    }
}
