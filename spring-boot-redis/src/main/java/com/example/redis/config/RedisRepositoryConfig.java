package com.example.redis.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

//    @Bean(destroyMethod = "shutdown")
//    ClientResources clientResources() {
//        return DefaultClientResources.create();
//    }

//    @Bean
//    public ClientOptions clientOptions(){
//        return ClientOptions.builder()
//                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
//                .autoReconnect(true)
//                .build();
//    }

//    @Bean
//    LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options, ClientResources dcr) {
//        return LettucePoolingClientConfiguration.builder()
//                .poolConfig()
//                .clientOptions(options)
//                .clientResources(dcr)
//                .build();
//    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return new RedisStandaloneConfiguration(redisHost, redisPort);
    }

    @Bean
    public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // key, value 문자로 저장하려면....
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 객체를 json 형태로 깨지지 않고 받기 위한 직렬화 작업
        // redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer<>(RedisToken.class)); // 표준화된 VO 사용시
        return redisTemplate;
    }
}
