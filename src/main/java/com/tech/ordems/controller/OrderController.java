package com.tech.ordems.controller;

import com.tech.ordems.controller.dto.ApiResponseDTO;
import com.tech.ordems.controller.dto.OrderResponseDTO;
import com.tech.ordems.controller.dto.PaginationResponseDTO;
import com.tech.ordems.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> listOrders(@PathVariable("customerId") Long customerId,
                                                                       @RequestParam(name= "page",defaultValue = "0")Integer page,
                                                                       @RequestParam(name= "pageSize", defaultValue = "10")Integer pageSize) {
        Page<OrderResponseDTO> body=orderService.findAllByCustomerId(customerId, PageRequest.of(page,pageSize));
        return ResponseEntity.ok(new ApiResponseDTO<>(
                body.getContent(),
                PaginationResponseDTO.fromPage(body)
        ));
    }


}
