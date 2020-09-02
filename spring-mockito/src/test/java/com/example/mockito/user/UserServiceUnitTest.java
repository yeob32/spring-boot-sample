package com.example.mockito.user;

import com.example.mockito.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setMockOutput() {
        // 주로
        // Stubbing
//        when(userRepository.getUser(1L)).thenReturn(user);
    }

    @Test
    public void get_user() {
        User user = getUser();

        when(userRepository.findUser(user.getId())).thenReturn(user);
        User findUser = userService.getUser(user.getId());

        verify(userRepository).findUser(anyLong());
    }

    @Test
    public void verify_get_user() {
        User user = getUser();

        given(userRepository.findUser(user.getId())).willReturn(user);
        User findUser = userService.getUser(user.getId());

        // 행동에 대한 테스트
        verify(userRepository).findUser(user.getId());
        verify(userRepository, times(1)).findUser(user.getId());

        verify(userRepository, never()).findUser(333L);

        // mock의 행동이 모두 검증 되었는지 확인한다
        verifyNoMoreInteractions(userRepository);

        assertThat(findUser).isEqualTo(user);
        assertThat(findUser.getName()).isEqualTo("yeob32");
    }

    @Test
    public void bdd_test() {
        User user = getUser();

        // given
        given(userRepository.findUser(user.getId())).willReturn(user);
        // when
        User findUser = userService.getUser(user.getId());
        // then
        assertThat(findUser).isEqualTo(user);
        assertThat(findUser.getName()).isEqualTo("yeob32");
    }

    @Test
    public void arg_matchers() {
        when(userRepository.findUser(anyLong())).thenReturn(new User(123L, "xxx"));
        User findUser = userService.getUser(anyLong());

        verify(userRepository).findUser(anyLong());
    }

    @Test
    public void with_exceptions() {
//        when(userRepository.findAll().get(anyInt())).thenThrow(new RuntimeException());
//
//        userRepository.findAll().get(anyInt());
    }

    User getUser() {
        return new User(1L, "yeob32");
    }
}
