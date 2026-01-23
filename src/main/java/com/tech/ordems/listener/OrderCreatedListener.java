package com.tech.ordems.listener;

import com.tech.ordems.listener.dto.OrderCreatedEventDTO;
import com.tech.ordems.useCase.CreateOrderUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.tech.ordems.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);
    private final CreateOrderUseCase createOrderUseCase; // Injeção da interface do Use Case

    public OrderCreatedListener(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEventDTO> message){
        log.info("Message consumed: {}", message);
        createOrderUseCase.execute(message.getPayload());
    }
}