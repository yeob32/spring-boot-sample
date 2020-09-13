package com.example.async.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDto {

    private long productId;

    public Order toEntity() {
        return new Order();
    }
}
