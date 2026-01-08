package com.tech.ordems.service;

import com.tech.ordems.entity.OrderEntity;
import com.tech.ordems.entity.OrderItem;
import com.tech.ordems.listener.dto.OrderCreatedEventDTO;
import com.tech.ordems.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
     public void save(OrderCreatedEventDTO event) {
        var entity=new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());

        entity.setItems(getOrdemItens(event));
        entity.setTotal(getTotal(event));
        orderRepository.save(entity);
     }

    private BigDecimal getTotal(OrderCreatedEventDTO event) {
        return  event.itens().stream().map(i->i.preco().multiply(BigDecimal.valueOf(i.quantidade())) ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @NonNull
    private static List<OrderItem> getOrdemItens(OrderCreatedEventDTO event) {
        return event.itens().stream().map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco())).toList();
    }
}
