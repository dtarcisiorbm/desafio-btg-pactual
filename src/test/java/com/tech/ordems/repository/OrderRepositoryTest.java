package com.tech.ordems.repository;

import com.tech.ordems.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void deveCalcularTotalDePedidosPorCliente() {
        // Arrange
        Long customerId = 123L;

        var order1 = new OrderEntity();
        order1.setOrderId(1L);
        order1.setCustomerId(customerId);
        order1.setTotal(new BigDecimal("100.00"));

        var order2 = new OrderEntity();
        order2.setOrderId(2L);
        order2.setCustomerId(customerId);
        order2.setTotal(new BigDecimal("150.50"));

        mongoTemplate.save(order1, "tb_orders");
        mongoTemplate.save(order2, "tb_orders");

        // Act
        BigDecimal total = orderRepository.findTotalOnOrderByCustomerId(customerId);

        // Assert
        assertEquals(new BigDecimal("250.50"), total);
    }
}