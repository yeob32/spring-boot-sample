package com.example.redis.foo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FooRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Object> jsonRedisTemplate;

    public void save() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("foo", "111");
    }

    public String get() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get("foo");
    }

    public void save1() {
        ValueOperations<String, Object> valueOperations = jsonRedisTemplate.opsForValue();
        valueOperations.set("foo_json", "111");
    }

    public String get1() {
        ValueOperations<String, Object> valueOperations = jsonRedisTemplate.opsForValue();
        Object value = valueOperations.get("foo_json");
        return (value != null) ? value.toString() : "";
    }
}
