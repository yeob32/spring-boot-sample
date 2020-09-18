package com.example.redis.point;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRedisRepository extends CrudRepository<Point, String> {
}
