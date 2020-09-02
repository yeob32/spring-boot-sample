package com.example.mockito.mock;

import com.example.mockito.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class MockTest {

    @Test
    public void givenCountMethodMocked_WhenCountInvoked_ThenMockedValueReturned() {
        UserRepository localMockRepository = mock(UserRepository.class);
        Mockito.when(localMockRepository.count()).thenReturn(111L);

        long userCount = localMockRepository.count();

        Assertions.assertThat(111L).isEqualTo(userCount);
        Mockito.verify(localMockRepository).count();
    }


}
