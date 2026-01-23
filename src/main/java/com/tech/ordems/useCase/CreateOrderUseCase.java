package com.tech.ordems.useCase;

import com.tech.ordems.listener.dto.OrderCreatedEventDTO;

public interface CreateOrderUseCase {
    void execute(OrderCreatedEventDTO event);
}
