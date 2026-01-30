package com.tech.ordems.useCase;

import com.tech.ordems.entity.OrderEntity;
import com.tech.ordems.listener.dto.OrderCreatedEventDTO;
import com.tech.ordems.listener.dto.OrderItemEvent;
import com.tech.ordems.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CreateOrderUseCaseImpl createOrderUseCase;

    @Test
    @DisplayName("Deve criar um pedido com o cálculo do total correcto")
    void deveCriarPedidoComSucesso() {
        // Arrange
        var item1 = new OrderItemEvent("Produto A", 2, new BigDecimal("50.00"));
        var item2 = new OrderItemEvent("Produto B", 1, new BigDecimal("25.00"));
        var event = new OrderCreatedEventDTO(1L, 10L, List.of(item1, item2));

        // Act
        createOrderUseCase.execute(event);

        // Assert
        var captor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(orderRepository).save(captor.capture());

        OrderEntity savedOrder = captor.getValue();
        assertEquals(1L, savedOrder.getOrderId());
        assertEquals(10L, savedOrder.getCustomerId());
        // Total esperado: (2 * 50) + (1 * 25) = 125.00
        assertEquals(new BigDecimal("125.00"), savedOrder.getTotal());
        assertEquals(2, savedOrder.getItems().size());
    }
}