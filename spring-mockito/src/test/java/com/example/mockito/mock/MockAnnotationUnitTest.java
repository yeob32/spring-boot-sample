package com.example.mockito.mock;

import com.example.mockito.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockAnnotationUnitTest {

    @Mock
    UserRepository mockRepository;

    @Test
    public void givenCountMethodMocked_WhenCountInvoked_ThenMockValueReturned() {
        when(mockRepository.count()).thenReturn(123L);

        long userCount = mockRepository.count();

        Assertions.assertThat(123L).isEqualTo(userCount);
        verify(mockRepository).count();
    }
}
