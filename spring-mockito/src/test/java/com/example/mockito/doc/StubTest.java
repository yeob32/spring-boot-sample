package com.example.mockito.doc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StubTest {

    @Test
    public void verify_behaviour() {
        //mock creation
        List<String> mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void stubbing() {
        //You can mock concrete classes, not just interfaces
        LinkedList<String> mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        assertThat(mockedList.get(0)).isEqualTo("first");

        //following throws runtime exception
        assertThatThrownBy(() -> { throw new Exception("boom!"); }).hasMessage("boom!");

        Throwable thrown = catchThrowable(() -> System.out.println(mockedList.get(1)));
        assertThat(thrown).isInstanceOf(Exception.class);

        //following prints "null" because get(999) was not stubbed
        assertThat(mockedList.get(999)).isNull();

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed.
        verify(mockedList).get(0); //ordinary verification
    }

    @Test
    public void argument_matchers() {
        LinkedList<String> mockedList = mock(LinkedList.class);

        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
//        when(mockedList.contains(argThat(isValid()))).thenReturn(true);

        //following prints "element"
        assertThat(mockedList.get(999)).isEqualTo("element");

        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        //argument matchers can also be written as Java 8 Lambdas
//        verify(mockedList).add(argThat(someString -> someString.length() > 5));

//        verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
        //above is correct - eq() is also an argument matcher

//        verify(mock).someMethod(anyInt(), anyString(), "third argument");
        //above is incorrect - exception will be thrown because third argument is given without an argument matcher.
    }

    @Test
    @DisplayName("호출 횟수 확인")
    public void verifying_exact_number_of_invocations() {
        LinkedList<String> mockedList = mock(LinkedList.class);

        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atMostOnce()).add("once");
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");
    }

    @Test
    public void stubbing_void_methods_with_exceptions() {
        LinkedList<String> mockedList = mock(LinkedList.class);

        doThrow(new RuntimeException("foo")).when(mockedList).clear();

        //following throws RuntimeException:
        assertThatThrownBy(mockedList::clear)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("foo");
    }

    @Test
    public void verification_in_order() {
        // A. Single mock whose methods must be invoked in a particular order
        List<String> singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        //following will make sure that add is first called with "was added first", then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List<String> firstMock = mock(List.class);
        List<String> secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        InOrder inOrder2 = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder2.verify(firstMock).add("was called first");
        inOrder2.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }

}
