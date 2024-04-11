package com.ladyshopee.api.repository;

import com.ladyshopee.api.model.ProductCodeCounter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeCounterRepository extends MongoRepository<ProductCodeCounter, String> {
}
