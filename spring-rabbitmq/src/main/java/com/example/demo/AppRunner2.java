package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class AppRunner2 implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitReceiver rabbitReceiver;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Thread.currentThread().getName() + " -- Sending message...");

        IntStream.range(0, 5000)
                .forEach(i -> {
                    CustomMessage customMessage = new CustomMessage("weather"+i, "clouding");
                    rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "foo2.bar.bax", customMessage);
                });

        rabbitReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
