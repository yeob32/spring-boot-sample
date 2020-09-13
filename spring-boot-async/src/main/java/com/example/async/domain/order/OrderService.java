package com.example.async.domain.order;

import com.example.async.domain.cart.CartService;
import com.example.async.event.order.OrderCompletedEvent;
import com.example.async.infra.EmailSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final EmailSendService emailSendService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Order order(OrderDto orderDto) {
        log.info("order transaction name : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        Order order = orderRepository.save(orderDto.toEntity());

        // 트랜잭션 문제 -> 해당 메서드 에러 발생 시 주문 롤백 됨
        // 성능 문제 -> 동기 방식이기 때문에 해당 메서드 처리 지연 시 주문 프로세스 처리 지연
//        cartService.deleteCartWithOrder(order);
        eventPublisher.publishEvent(new OrderCompletedEvent(order));

        return order;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
