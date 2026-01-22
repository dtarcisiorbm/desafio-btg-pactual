package com.tech.ordems.repository;

import java.math.BigDecimal;

public interface OrderCustomRepository {
    BigDecimal findTotalOnOrderByCustomerId(Long customerId);
}