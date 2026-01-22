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
public interface OrderService {

    void save(OrderCreatedEventDTO event);
    Page<OrderResponseDTO> findAllByCustomerId(Long customerId, PageRequest pageRequest);
    BigDecimal findTotalOnOrderByCustomerId(Long customerId);
}
