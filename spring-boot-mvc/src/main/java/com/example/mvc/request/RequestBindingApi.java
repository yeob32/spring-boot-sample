package com.example.mvc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class RequestBindingApi {

    @PostMapping("/request1")
    public ResponseEntity<RequestDto> requestBody1(RequestDto requestDto) {
        log.info("requestDto1 : {}", requestDto);
        return ResponseEntity.ok(requestDto);
    }

    @PostMapping("/request2")
    public ResponseEntity<RequestDto> requestBody2(@RequestBody RequestDto requestDto) {
        log.info("requestDto2 : {}", requestDto);
        return ResponseEntity.ok(requestDto);
    }

    @PostMapping("/request3")
    public String requestBody3(@ModelAttribute RequestDto requestDto) {
        log.info("requestDto3 : {}", requestDto);
        return "";
    }

    @Getter
    @Setter
    @ToString
    static class RequestDto {
        String email;
        int password;
    }
}
