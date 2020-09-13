package com.example.async.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderApi {

    private final OrderService orderService;

    @PostMapping("/orders/order")
    public Order order(@RequestBody OrderDto orderDto) {
        return orderService.order(orderDto);
    }

    @GetMapping("/orders/order")
    public List<Order> orders() {
        return orderService.getOrders();
    }
}
