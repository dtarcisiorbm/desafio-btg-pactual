package com.tech.ordems.controller.dto;

import java.math.BigDecimal;

public record OrderResponseDTO(Long orderId, Long customerId, BigDecimal total) {
}
