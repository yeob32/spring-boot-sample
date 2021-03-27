package com.example.demo.domain.user;

import com.example.demo.domain.model.BaseAuditEntity;
import com.example.demo.domain.user.model.Email;
import com.example.demo.domain.user.model.Password;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_no")
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
