# Spring MVC

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



## References
- https://www.baeldung.com/spring-boot-add-filter