package com.ladyshopee.api.service;

import com.ladyshopee.api.payload.dto.TransactionDTO;
import com.ladyshopee.api.payload.response.TransactionProductResponse;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionProductResponse createTransaction(TransactionDTO transactionDTO);
    List<TransactionDTO> getTransactionsByDateRange(LocalDate fromDate, LocalDate toDate);
}
