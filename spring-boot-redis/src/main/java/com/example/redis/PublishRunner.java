package com.example.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class PublishRunner implements ApplicationRunner {

    private final RedisTemplate<String, String> stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IntStream.range(0, 5).forEach(i -> stringRedisTemplate.convertAndSend("event", "읭 ? " + i));
    }
}
