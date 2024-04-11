package com.ladyshopee.api.repository;

import com.ladyshopee.api.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
