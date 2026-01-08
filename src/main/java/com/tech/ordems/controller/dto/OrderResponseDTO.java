package com.tech.ordems.controller.dto;

import com.tech.ordems.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponseDTO(Long orderId, Long customerId, BigDecimal total) {
public static OrderResponseDTO fromEntity(OrderEntity entity) {
    return new OrderResponseDTO(entity.getOrderId(), entity.getCustomerId(), entity.getTotal());
}
}
