package com.example.mvc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
public class TestApi {

    @GetMapping("/boo")
    public void boo() {
        log.info("called boo !!!");
    }

    @GetMapping("/foo")
    public Foo foo(FooDto dto) {
        log.info("called foo !!");
        return dto.toEntity();
    }

    @GetMapping("/hello")
    public void hello() {
        log.info("hello !!!!!!!!");
    }

    @AllArgsConstructor
    @Data
    public static class Foo {
        String name;
        LocalDate createdAt;
    }

    @Data
    public static class FooDto {
        String name;

        public Foo toEntity() {
            return new Foo(name, LocalDate.now());
        }
    }
}
