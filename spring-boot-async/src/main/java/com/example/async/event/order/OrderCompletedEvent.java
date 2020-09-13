package com.example.async.event.order;

import com.example.async.domain.order.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderCompletedEvent {

    private final Order order;
}
