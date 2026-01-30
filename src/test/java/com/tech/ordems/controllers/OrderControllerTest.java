package com.tech.ordems.controllers;

import com.tech.ordems.controller.OrderController;
import com.tech.ordems.controller.dto.OrderResponseDTO;
import com.tech.ordems.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void deveRetornarVinteDuzentosComDadosPaginados() throws Exception {
        // Arrange
        Long customerId = 1L;
        var orderResponse = new OrderResponseDTO(101L, customerId, new BigDecimal("150.00"));
        var page = new PageImpl<>(List.of(orderResponse), PageRequest.of(0, 10), 1);

        when(orderService.findAllByCustomerId(eq(customerId), any())).thenReturn(page);
        when(orderService.findTotalOnOrderByCustomerId(customerId)).thenReturn(new BigDecimal("150.00"));

        // Act & Assert
        mockMvc.perform(get("/customers/{customerId}/orders", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary.totalOnOrders").value(150.00))
                .andExpect(jsonPath("$.data[0].orderId").value(101))
                .andExpect(jsonPath("$.paginationResponse.totalElements").value(1));
    }
}