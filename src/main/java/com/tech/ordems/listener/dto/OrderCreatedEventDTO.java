package com.tech.ordems.listener.dto;



import java.util.List;

public record OrderCreatedEventDTO(Long codigoPedido, Long codigoCliente, List<OrderItemEvent>itens ) {
}
