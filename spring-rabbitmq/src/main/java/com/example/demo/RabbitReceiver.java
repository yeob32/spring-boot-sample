package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * 카운트를 설정하고 await 상태로 대기
 * 카운트 0이 되면 종료
 */
@Component
public class RabbitReceiver {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println(Thread.currentThread().getName() + " -- Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
