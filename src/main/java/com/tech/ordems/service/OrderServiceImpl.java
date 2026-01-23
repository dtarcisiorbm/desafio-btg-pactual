package com.tech.ordems.service;

import com.tech.ordems.controller.dto.OrderResponseDTO;
import com.tech.ordems.entity.OrderEntity;
import com.tech.ordems.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Método save e métodos privados auxiliares foram removidos e movidos para o UseCase

    @Override
    public Page<OrderResponseDTO> findAllByCustomerId(@NonNull Long customerId, PageRequest pageRequest) {
        Page<OrderEntity> orders = orderRepository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(OrderResponseDTO::fromEntity);
    }

    @Override
    public BigDecimal findTotalOnOrderByCustomerId(@NonNull Long customerId) {
        return orderRepository.findTotalOnOrderByCustomerId(customerId);
    }
}