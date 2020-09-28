# Spring MVC

## Servlet
### 특징
- 클라이언트의 요청에 대해 동적으로 작동하는 웹 어플리케이션 컴포넌트
- html 을 사용하여 요청에 응답한다.
- Java Thread 를 이용하여 동작한다.
- MVC 패턴에서 Controller 로 이용된다.
- HTTP 프로토콜 서비스를 지원하는 javax.servlet.http.HttpServlet 클래스를 상속받는다. 
- UDP 보다 속도가 느리다.
- HTML 변경 시 Servlet 을 재컴파일해야 하는 단점이 있다.

### 동작 방식
- 클라이언트가 URL 을 클릭하면 HTTP Request 를 Servlet Container 로 전송
- HTTP Request 를 전송받은 Servlet Container 는 HttpServletRequest, HttpServletResponse 두 객체를 생성
- web.xml 은 사용자가 요청한 URL 을 분석하여 어느 서블릿에 대해 요청을 한 것인지 찾는다.
- 해당 서블릿에서 service 메소드를 호출한 후 클리아언트의 POST, GET 여부에 따라 doGet() 또는 doPost() 호출
- doGet() or doPost() 메소드는 동적 페이지를 생성한 후 HttpServletResponse 객체에 응답을 보낸다.
- 응답이 끝나면 HttpServletRequest, HttpServletResponse 두 객체를 소멸시킨다.

## Servlet Container
- 클라이언트의 요청(Request)을 받아주고 응답(Response)할 수 있게, 웹서버와 소켓을 만들어 통신
- ex) tomcat

### 역할
1. 웹서버와의 통신 지원
- 서블릿 컨테이너는 서블릿과 웹서버가 손쉽게 통신할 수 있게 해준다. 
- 일반적으로 우리는 소켓을 만들고 listen, accept 등을 해야하지만 서블릿 컨테이너는 이러한 기능을 API 로 제공하여 복잡한 과정을 생략할 수 있게 해준다.
    - 개발자가 서블릿에 구현해야 할 비지니스 로직 구현에 집중

2. 서블릿 생명주기(Life Cycle) 관리 
- 서블릿 컨테이너는 서블릿의 생명주기 관리. 
- 서블릿 클래스를 로딩하여 인스턴스화, 초기화 메소드 호출, 요청이 들어오면 적절한 서블릿 메소드 호출 
- 또한 서블릿이 생명을 다 한 순간에는 적절하게 Garbage Collection(가비지 컬렉션)을 진행하여 편의 제공

3. 멀티쓰레드 지원 및 관리 
- 서블릿 컨테이너는 요청이 올 때 마다 새로운 자바 스레드 생성
- HTTP 서비스 메소드 실행 후 스레드는 자동으로 죽는다. 
- 원래는 스레드를 관리해야 하지만 서버가 다중 쓰레드를 생성 및 운영해주니 스레드의 안정성에 대해서 걱정하지 않아도 된다.

4. 선언적인 보안 관리 
- 서블릿 컨테이너를 사용하면 개발자는 보안에 관련된 내용을 서블릿 또는 자바 클래스에 구현해 놓지 않아도 된다.
- 일반적으로 보안관리는 XML 배포 서술자에다가 기록하므로, 보안에 대해 수정할 일이 생겨도 자바 소스 코드를 수정하여 다시 컴파일 하지 않아도 보안관리 가능

## Filter
- 서블릿 3.0 이전까지는 web.xml 을 통해 Servlet, Filter 등록 및 url 맵핑 설정 
- 서블릿 3.0 부터는 Java 어노테이션으로 web.xml 설정 대체 가능

### implements Filter
```
@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.info("custom filter request !!");
    chain.doFilter(request, response);
    log.info("custom filter response !!");
}
```

### FilterRegistrationBean
```
@Bean
public FilterRegistrationBean<Filter> loggingFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new CustomFilter());
    registrationBean.addUrlPatterns("/foo/*");
    return registrationBean;
}
```

### @WebFilter
```
// @WebFilter(dispatcherTypes= {DispatcherType.REQUEST}, { "/test-filter", "/test/*" })
// @WebFilter(value = {"*.jsp", "/test"})
@WebFilter(urlPatterns = "/boo")
public class CustomFilter implements Filter {

}
```

- DispatcherType
    - REQUEST : url 을 통해 들어올 경우.
    - INCLUDE : include() 를 통해(<jsp:include ..>) 를 통해 들어올 경우.
    - FORWARD : forward() 를 통해(<jsp:forward ..>) 를 통해 들어올 경우.
    - ERROR : <%@ page errorPage="..." %>를 통해 에러페이지로 이동할 경우.

## Listener

## Controller

### DispatcherServlet
- Front Controller Pattern
![mvc](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/images/mvc.png)

### Controller
- View 를 반환하기 위해 사용
- request -> DispatcherServlet(Type, Handler Mapping) -> View

### RestController 
- @ResponseBody 
- Json 형태로 변환된 데이터 반환
- request -> DispatcherServlet(Type, Handler Mapping) -> Data

### MessageConverter
- Http 요청 본문 변환 -> only POST
- @RequestBody / @ResponseBody

## Request Binding
- dto getter 필수
### @RequestBody
- application/json 으로 받은 요청을 MessageConverter 를 통해 Java 객체로 변환
- dto setter 없어도 됨
### @ModelAttribute 또는 기본으로 받을 때
- dto setter 있어야 됨 -> - 전달받은 파라미터들을 JavaObject 로 매핑
- application/x-www-form-urlencoded
### @RequestParam
- default required true


## References
- https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc
- https://www.ntu.edu.sg/home/ehchua/programming/java/javaservlets.html
- https://www.baeldung.com/spring-boot-add-filter
