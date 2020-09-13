package com.example.async.domain.cart;

import com.example.async.domain.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

    @Transactional
    public void deleteCartWithOrder(Order order) {
        log.info("deleteCartWithOrder transaction name : {}", TransactionSynchronizationManager.getCurrentTransactionName());

        throw new RuntimeException("deleteCartWithOrder Error");
    }
}
