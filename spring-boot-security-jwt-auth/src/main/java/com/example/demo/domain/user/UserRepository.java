package com.example.demo.domain.user;

import com.example.demo.domain.user.model.Email;
import com.example.demo.domain.user.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(Email email);
    boolean existsByEmail(Email email);
    Optional<User> findByEmailAndPassword(Email email, Password password);
}
