package com.example.redis.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedisPostTest {
    @Autowired
    PostRepository postRepository;

    @Test
    public void post_test() {
        long postId = 1L;
        postRepository.delete(postId);

        for (int i = 0; i < 1000; i++) {
            postRepository.save(postId, i);
        }

        List<Post> posts = postRepository.getPosts(postId);
        assertThat(posts.size()).isEqualTo(1000);
    }

    @Test
    public void post_concurrency_test() {
        long postId = 1L;
        postRepository.delete(postId);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                postRepository.save(postId, finalI);
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Post> posts = postRepository.getPosts(postId);
        assertThat(posts.size()).isEqualTo(1000);
    }

    @Test
    public void post_concurrency_test2() {
        int count = 10000;
        long postId = 1L;
        postRepository.delete(postId);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.execute(() -> {
                postRepository.save(postId, finalI);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<Post> posts = postRepository.getPosts(postId);
        assertThat(posts.size()).isEqualTo(count);
    }
}
