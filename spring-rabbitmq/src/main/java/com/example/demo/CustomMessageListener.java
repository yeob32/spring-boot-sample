package com.example.demo;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = RabbitMQConfig.topicExchangeName, type = "topic", durable = "true"),
            key = "foo2.bar.#"))
    public void handleCustomMessage(CustomMessage message) {
        System.out.println("handleCustomMessage : " + message.toString());
    }
}
