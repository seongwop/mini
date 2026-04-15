package com.example.mini.order;

import lombok.Getter;

@Getter
public class OrderResponse {
    private final Long orderId;
    private final String productName;

    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.productName = order.getProduct().getName();
    }
}
