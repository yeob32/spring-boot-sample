# Mockito
- JUnit 프레임워크
- Mocking, Verification

## Stub
> dummy 객체가 실제로 동작하는 것처럼 보이게 만들어 놓은 객체
- 외부 서비스 (database, api) 구현을 하드코딩된 테스트용으로 대체 
- 비즈니스 로직에 집중 할 수 있도록 도와준다
- 의존하는 것에 독립적으로 개발/테스트 가능 (연동 포인트에 문제가 있더라도 테스트 가능)

## Test Behavior
- 테스트 대상의 결과값에는 신경쓰지 않는다
- 의도한 대로 비즈니스 로직이 행동하는지 테스트한다

## Test State
- 특정한 메서드를 거친 후, 객체의 상태에 대해 예상값과 비교하는 방식

## Mock
> 껍데기만 있는 객체

### @MockBean
- org.springframework.boot.test.mock.mockito.MockBean
- 스프링 Bean이 아닌 Mock Bean을 주입
- 기존에 사용되던 Bean의 껍데기만 가져오고 내부의 구현 부분은 모두 사용자에게 위임한 형태

## @Spy
> 실제 인스턴스를 사용해서 mocking
- 예시로 spy 로 래핑한 객체가 있을 때 A함수는 실제 객체의 함수, B함수는 stubbing 하는 형태로 활용 가능 

### @SpyBean
- 스프링 컨텍스트의 모든 빈을 스파이로 래핑


## MockitoBBD
> Behavior Driven Development
> given, when, then
>> Mockito.when() 을 이용한 Stubbing 대신 BDDMockito.given(Object) 사용 시 자연스러운 테스트케이스 작성 가능
- BDDMockito.given(Object)
```
public void test() throws Exception {
  //given  
  given(object.someMethod()).willReturn(object);

  //when
  Object object = object.someMethod();

  //then
  assertThat(object, object);
}
```

### @ExtendWith
- JUnit5 에서 반복적으로 실행되는 클래스나 메서드에 선언한다. 
    - `MockitoExtension` 
    - `SpringExtension`: 스프링 5에 추가된 JUnit 5의 주피터(Jupiter) 모델에서 스프링 테스트컨텍스트(TestContext)를 사용할 수 있도록 해준다.

## References
- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#argument_matchers