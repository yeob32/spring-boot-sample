package com.example.redis.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostRepository {
    private final RedisTemplate<String, Object> jsonRedisTemplate;
    private static final String KEY = "posts:";

    public void save(long postId, long postSubId) {
        Object value = Map.of(
                "postId", postId,
                "postSubId", postSubId,
                "name", "post"
        );
        ListOperations<String, Object> listOperations = jsonRedisTemplate.opsForList();
        listOperations.rightPush(getKey(postId), value);
    }

    public List<Post> getPosts(long postId) {
        List<Object> posts = jsonRedisTemplate.opsForList().range(getKey(postId), 0, -1);
        if (posts == null) {
            throw new RuntimeException();
        }

        return posts.stream().map(post -> {
            Map<?, ?> postMap = (Map<?, ?>) post;
            return new Post(
                    Long.parseLong(postMap.get("postId").toString()),
                    Long.parseLong(postMap.get("postSubId").toString()),
                    postMap.get("name").toString()
            );
        }).collect(Collectors.toList());
    }

    public void delete(long postId) {
        jsonRedisTemplate.delete(getKey(postId));
    }

    private String getKey(long postId) {
        return KEY + postId;
    }
}
