package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitReceiver rabbitReceiver;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Thread.currentThread().getName() + " -- Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        rabbitReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
