package com.example.demo.global.config;

import com.example.demo.domain.user.User;
import com.example.demo.global.security.model.UserContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@EnableJpaAuditing
@EnableTransactionManagement
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<User> jwtTokenAuditorAware() {
        return () -> Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(authentication -> (UserContext) authentication.getPrincipal())
                .map(context -> User.builder().id(context.getUserNo()).build());
    }
}
