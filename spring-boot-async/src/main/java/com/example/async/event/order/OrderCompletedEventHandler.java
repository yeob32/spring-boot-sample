package com.example.async.event.order;

import com.example.async.domain.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCompletedEventHandler {

    private final CartService cartService;

    @Async // 비동기 처리, 해당 메서드는 기존 스레드와 분리
//    @TransactionalEventListener
    @EventListener
    public void orderCompletedEventListener(OrderCompletedEvent orderCompletedEvent) {
        log.info("orderCompletedEventListener transaction name : {}", TransactionSynchronizationManager.getCurrentTransactionName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("start deleteCartWithOrder");
        cartService.deleteCartWithOrder(orderCompletedEvent.getOrder());
    }
}
