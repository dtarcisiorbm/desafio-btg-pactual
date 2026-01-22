package com.tech.ordems.repository;

import com.tech.ordems.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

// Adicionado: extends OrderCustomRepository
public interface OrderRepository extends MongoRepository<OrderEntity, Long>, OrderCustomRepository {

    Page<OrderEntity> findAllByCustomerId(Long customerId, PageRequest pageRequest);
}