package com.example.demo.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtil {

    public static Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true); // XSS 완화
        cookie.setMaxAge(60 * 60 * 24); // 1 day

        return cookie;
    }

}
