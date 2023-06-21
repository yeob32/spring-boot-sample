package com.example.redis.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Post {
    Long postId;
    Long postSubId;
    String name;

    public Post(Long postId, Long postSubId, String name) {
        this.postId = postId;
        this.postSubId = postSubId;
        this.name = name;
    }
}
