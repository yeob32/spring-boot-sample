package com.example.demo.domain.user;

import com.example.demo.domain.user.dto.RequestUserDto;
import com.example.demo.domain.user.model.Email;
import com.example.demo.domain.user.model.Password;
import com.example.demo.global.security.utils.UserContextUtil;
import com.example.demo.global.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public boolean isExistUser(User user) {
        return userRepository.existsByEmail(user.getEmail());
    }

    @Transactional(readOnly = true)
    public User getUserBySignIn(String email, String password) {
        return userRepository.findByEmailAndPassword(Email.of(email), Password.of(password))
                .orElseThrow(RuntimeException::new);
    }

    public User updateUser(Long id, RequestUserDto.ReqUpdate dto) {
        User findUser = userRepository.findById(id).orElseThrow(RuntimeException::new);
        findUser.setName(dto.getName());
        userRepository.save(findUser);
        sessionService.updateSessionUser(UserContextUtil.getSessionKey(), findUser);

        return findUser;
    }

    public User createUser() {
        return userRepository.save(User.builder()
                .email(Email.of("testuser01@gmail.com"))
                .password(Password.of("1234"))
                .build());
    }
}
