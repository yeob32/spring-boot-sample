package com.example.demo.domain.user;

import com.example.demo.domain.user.model.Email;
import com.example.demo.domain.user.model.Password;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private Email email;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    private Password password;

    @Builder
    public User(Long id, Email email, String name, Password password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
