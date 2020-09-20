package com.example.demo.domain.user;

import com.example.demo.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserApi {

    /**
     * TODO 로그인 처리 어디서 해야할까.. 1. Controller ??, 2. Security ??
     * 1. 사용자 조회 및 비밀번호 체크
     * 2. 정상 조회 시 세션에 담고 JWT 쿠키로 내려준다.
     *   -> 세션??, 리프레시 토큰?? ---> 유저 정보 계속 조회해야하나..?
     *   -> 세션은 expire time 갱신 우쨔..?
     * 3. 쿠키와 함께 API 요청 시 -> 세션 맵 조회 성공 시 데이터 내려줘.
     */

    @PostMapping("/login")
    public void login(String id, String password) {
        System.out.println("id : " + id + ", password : " + password);
    }

    @GetMapping("/member")
    public List<String> members() {
        return Arrays.asList("member1", "member2");
    }

    @GetMapping("/posts")
    public List<String> posts() {
        return Arrays.asList("post1", "post2");
    }
}
