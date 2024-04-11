package com.ladyshopee.api.service.impl;

import com.ladyshopee.api.model.Transaction;
import com.ladyshopee.api.payload.dto.ProductDTO;
import com.ladyshopee.api.payload.dto.TransactionDTO;
import com.ladyshopee.api.payload.response.TransactionProductResponse;
import com.ladyshopee.api.repository.TransactionRepository;
import com.ladyshopee.api.service.ProductService;
import com.ladyshopee.api.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private ProductService productService;

    @Override
    @Transactional
    public TransactionProductResponse createTransaction(TransactionDTO transactionDTO) {
        ProductDTO savedProduct = productService.processTransaction(transactionDTO);

        Transaction transaction = Transaction.builder()
                .productCode(transactionDTO.getProductCode())
                .sellPrice(transactionDTO.getSellPrice())
                .units(transactionDTO.getUnits())
                .desc(transactionDTO.getDesc())
                .phoneNo(transactionDTO.getPhoneNo())
                .colorAndSizes(transactionDTO.getColorAndSizes())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionProductResponse transactionProductResponse = TransactionProductResponse.builder()
                .product(savedProduct)
                .transaction(transactionToTransactionDTO(savedTransaction))
                .build();
        return transactionProductResponse;
    }

    @Override
    public List<TransactionDTO> getTransactionsByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<TransactionDTO> transactions = transactionRepository.findByCreatedAtBetween(
                fromDate.atStartOfDay(), toDate.atTime(23, 59, 59))
                .stream()
                .map(transaction -> transactionToTransactionDTO(transaction)).collect(Collectors.toList());
        return transactions;
    }

    private TransactionDTO transactionToTransactionDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .productCode(transaction.getProductCode())
                .sellPrice(transaction.getSellPrice())
                .units(transaction.getUnits())
                .desc(transaction.getDesc())
                .phoneNo(transaction.getPhoneNo())
                .colorAndSizes(transaction.getColorAndSizes())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
