package com.tech.ordems.service;

import com.tech.ordems.controller.dto.OrderResponseDTO;
import com.tech.ordems.entity.OrderEntity;
import com.tech.ordems.entity.OrderItem;
import com.tech.ordems.listener.dto.OrderCreatedEventDTO;
import com.tech.ordems.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService { // 1. Mudei para 'implements'

    private final OrderRepository orderRepository;
    // 2. Removi o MongoTemplate daqui completamente

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(OrderCreatedEventDTO event) {
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getOrdemItens(event));
        entity.setTotal(getTotal(event));

        orderRepository.save(entity);
    }

    @Override
    public Page<OrderResponseDTO> findAllByCustomerId(@NonNull Long customerId, PageRequest pageRequest) {
        Page<OrderEntity> orders = orderRepository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(OrderResponseDTO::fromEntity);
    }

    @Override
    public BigDecimal findTotalOnOrderByCustomerId(@NonNull Long customerId) {
        // 3. Agora delegamos para o repositório (que contem a lógica do MongoTemplate internamente)
        return orderRepository.findTotalOnOrderByCustomerId(customerId);
    }

    private BigDecimal getTotal(OrderCreatedEventDTO event) {
        return event.itens().stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static List<OrderItem> getOrdemItens(OrderCreatedEventDTO event) {
        return event.itens().stream()
                .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco()))
                .toList();
    }
}