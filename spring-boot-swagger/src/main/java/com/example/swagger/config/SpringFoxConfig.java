package com.example.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SpringFoxConfig {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }

    @Bean
    public Docket default_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("default")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.swagger.api"))
                .paths(PathSelectors.ant("/api/default/**"))
                .build()
                .apiInfo(apiInfo("v1", "default_api", "Swagger Default API Docs"));
    }

    @Bean
    public Docket custom_api() {

        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message("OK !!")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message("Not Found !!")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message("Internal Server Error !!")
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("custom")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.swagger.api"))
                .paths(PathSelectors.ant("/custom/**"))
                .build()
                .apiInfo(apiInfo("v1", "custom_api", "Swagger Default API Docs"))
                .globalResponseMessage(RequestMethod.GET, responseMessages);
    }


    @Bean
    public Docket foo_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("foo")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.swagger.api"))
                .paths(PathSelectors.ant("/foo/**"))
                .build()
                .apiInfo(apiInfo("v1", "foo_api", "Swagger Foo API Docs"));
    }

    private ApiInfo apiInfo(String version, String title, String description) {
        return new ApiInfoBuilder()
                .version(version)
                .title(title)
                .description(description)
                .contact(new Contact("Contact Me", "www.example.com", "foo@example.com"))
                .license("test Licenses")
                .licenseUrl("www.example.com")
                .extensions(new ArrayList<>())
                .build();
    }
}
