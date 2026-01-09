package com.tech.ordems.service;

import com.tech.ordems.controller.dto.OrderResponseDTO;
import com.tech.ordems.entity.OrderEntity;
import com.tech.ordems.entity.OrderItem;
import com.tech.ordems.listener.dto.OrderCreatedEventDTO;
import com.tech.ordems.repository.OrderRepository;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;
    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }
     public void save(OrderCreatedEventDTO event) {
        var entity=new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());

        entity.setItems(getOrdemItens(event));
        entity.setTotal(getTotal(event));
        orderRepository.save(entity);
     }

     public Page<OrderResponseDTO> findAllByCustomerId(@NonNull Long customerId, PageRequest pageRequest) {
         Page<OrderEntity> orders= orderRepository.findAllByCustomerId(customerId,pageRequest);
        return orders.map(OrderResponseDTO::fromEntity);
     }
    public BigDecimal findTotalOnOrderByCustomerId(@NonNull Long customerId) {
    var aggregations=newAggregation(
            match(Criteria.where("customerId").is(customerId)),group().sum("total").as("total")
    );
    var response=mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);
    return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }
    private BigDecimal getTotal(OrderCreatedEventDTO event) {
        return  event.itens().stream().map(i->i.preco().multiply(BigDecimal.valueOf(i.quantidade())) ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @NonNull
    private static List<OrderItem> getOrdemItens(OrderCreatedEventDTO event) {
        return event.itens().stream().map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco())).toList();
    }
}
