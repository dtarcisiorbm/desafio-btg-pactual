package com.tech.ordems.listener;

import com.tech.ordems.listener.dto.OrderCreatedEventDTO;
import com.tech.ordems.useCase.CreateOrderUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderCreatedListenerTest {

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @InjectMocks
    private OrderCreatedListener orderCreatedListener;

    @Test
    void deveChamarUseCaseAoReceberMensagem() {
        // Arrange
        var event = new OrderCreatedEventDTO(1L, 10L, Collections.emptyList());
        Message<OrderCreatedEventDTO> message = MessageBuilder.withPayload(event).build();

        // Act
        orderCreatedListener.listen(message);

        // Assert
        verify(createOrderUseCase).execute(event);
    }
}