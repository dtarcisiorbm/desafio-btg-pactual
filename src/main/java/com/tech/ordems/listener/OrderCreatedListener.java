package com.tech.ordems.listener;


import com.tech.ordems.listener.dto.OrderCreatedEventDTO;
import com.tech.ordems.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.tech.ordems.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {
    private final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);
    private final OrderService orderService;
    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }
 @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEventDTO> message){
     log.info("Message consumed:{}",message);
     orderService.save(message.getPayload());

 }
}
