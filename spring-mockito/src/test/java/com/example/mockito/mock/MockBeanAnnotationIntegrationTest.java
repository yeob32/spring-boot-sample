package com.example.mockito.mock;

import com.example.mockito.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MockBeanAnnotationIntegrationTest {

    @MockBean(name = "testUserRepository")
    UserRepository mockRepository;

    @Autowired
    ApplicationContext context;

    @Test
    public void givenCountMethodMocked_WhenCountInvoked_ThenMockValueReturned() {
        when(mockRepository.count()).thenReturn(123L);

        UserRepository userRepoFromContext = (UserRepository) context.getBean("testUserRepository");;
//        UserRepository userRepoFromContext = context.getBean(UserRepository.class);
        long userCount = userRepoFromContext.count();

        assertThat(123L).isEqualTo(userCount);
        verify(mockRepository).count();
    }
}