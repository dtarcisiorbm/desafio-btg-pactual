package com.tech.ordems.repository;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final MongoTemplate mongoTemplate;

    public OrderCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public BigDecimal findTotalOnOrderByCustomerId(Long customerId) {
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);

        // Tratamento simples para evitar NullPointerException caso não haja resultados
        var result = response.getUniqueMappedResult();
        if (result == null || result.get("total") == null) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(result.get("total").toString());
    }
}