package com.tech.ordems.service;

import com.tech.ordems.controller.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;

public interface OrderService {
    // Método save removido pois agora pertence ao UseCase
    Page<OrderResponseDTO> findAllByCustomerId(Long customerId, PageRequest pageRequest);
    BigDecimal findTotalOnOrderByCustomerId(Long customerId);
}