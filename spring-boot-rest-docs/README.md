# spring-rest-docs

## Rest Docs
- API 문서 자동화 도구
- Controller에 대한 Test Code를 기반으로 REST API 문서를 작성해준다.
- Test Code에서 제공하는 메서드를 통해 문서를 보완할 수 있다. 따라서 제품 코드에 영향을 주지 않는다.
- 실제 만들어지는 page는 html파일 하나이기 때문에 Spring Security를 적용하여 권한관리 하기 용이하다.

### Build Output directory
- Mave : target/generated-snippets
- Gradle : build/generated-snippets

### 음..
```
bootJar {
	dependsOn asciidoctor
	from ("${asciidoctor.outputDir}/html5") {
		into 'src/main/resources/static/docs'
	}
}
```

## AsciiDoc


### rest-assured vs MockMvc
- rest-assured
    - BDD 스타일로 직관적
    - @SpringBootTest 수행시 전체 컨텍스트 빈 주입으로 느림
- MockMvc
    - @WebMvcTest 컨트롤러 계층만 테스트하기 떄문에 빠름
    - 서비스 계층은 Mocking

## HATEOAS
> Hypermedia As The Engine Of Application State

### 특징
- 클라이언트가 서버로부터 어떠한 요청을 할 때, 요청에 필요한(의존되는) URI를 응답에 포함시켜 반환

### Why
- Rest API 의 단점 
    - endpoint URL 변경 시 관련 연결점 모두 수정해야한다.
    - 한 번 정해진 endpoint URL 은 변경이 어렵다.

### 장점
- 요청 URI가 변경되더라도 클라이언트에서 동적으로 생성된 URI를 사용함으로써, 클라이언트가 URI 수정에 따른 코드를 변경하지 않아도 되는 편리함을 제공합니다.
- URI 정보를 통해 들어오는 요청을 예측할 수 있게 됩니다.
- Resource가 포함된 URI를 보여주기 때문에, Resource에 대한 확신을 얻을 수 있습니다.    
    

  

### 이슈
```
urlTemplate not found. If you are using MockMvc did you use RestDocumentationRequestBuilders to build the request?
java.lang.IllegalArgumentException: urlTemplate not found. If you are using MockMvc did you use RestDocumentationRequestBuilders to build the request?
	at org.springframework.util.Assert.notNull(Assert.java:198)
	at org.springframework.restdocs.request.PathParametersSnippet.extractUrlTemplate(PathParametersSnippet.java:126)
	at org.springframework.restdocs.request.PathParametersSnippet.extractActualParameters(PathParametersSnippet.java:113)
	at org.springframework.restdocs.request.AbstractParametersSnippet.verifyParameterDescriptors(AbstractParametersSnippet.java:89)
	at org.springframework.restdocs.request.AbstractParametersSnippet.createModel(AbstractParametersSnippet.java:74)
	at org.springframework.restdocs.request.PathParametersSnippet.createModel(PathParametersSnippet.java:98)
```
- https://stackoverflow.com/questions/32889070/pathparameters-documentation-exception-urltemplate-not-found

### 참고
- https://www.baeldung.com/spring-rest-docs
- https://docs.spring.io/spring-restdocs/docs/2.0.4.RELEASE/reference/html5/
- https://m.blog.naver.com/PostView.nhn?blogId=songintae92&logNo=221410414713&proxyReferer=https:%2F%2Fwww.google.com%2F
- https://blog.woniper.net/219
