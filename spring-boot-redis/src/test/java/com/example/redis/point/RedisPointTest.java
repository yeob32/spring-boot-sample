package com.example.redis.point;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedisPointTest {

    @Autowired
    PointRedisRepository pointRedisRepository;

    @Test
    void create() {
        // given
        String pointId = "yeob32";
        LocalDateTime refreshTime = LocalDateTime.of(2020, 8, 28, 13, 36, 0);
        Point point = Point.builder()
                .id(pointId)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        // when
        pointRedisRepository.save(point);

        // then
        Point savedPoint = pointRedisRepository.findById(pointId).get();
        assertThat(savedPoint.getAmount()).isEqualTo(1000L);
        assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
    }

    @Test
    void update() {
        // given
        String pointId = "yeob32";
        LocalDateTime refreshTime = LocalDateTime.of(2020, 8, 28, 12, 0, 0);
        Point point = Point.builder()
                .id(pointId)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();
        pointRedisRepository.save(point);

        // when
        Point savedPoint = pointRedisRepository.findById(pointId).get();
        savedPoint.refresh(2000L, LocalDateTime.of(2020, 8, 29, 12, 0, 0));
        pointRedisRepository.save(savedPoint);

        // then
        Point refreshPoint = pointRedisRepository.findById(pointId).get();
        assertThat(refreshPoint.getAmount()).isEqualTo(2000L);
    }
}
