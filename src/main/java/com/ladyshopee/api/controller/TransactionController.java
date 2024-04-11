package com.ladyshopee.api.controller;

import com.ladyshopee.api.payload.dto.TransactionDTO;
import com.ladyshopee.api.payload.response.TransactionProductResponse;
import com.ladyshopee.api.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionProductResponse> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionProductResponse transactionProductResponse = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity(transactionProductResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getTransactions(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return new ResponseEntity(transactionService.getTransactionsByDateRange(fromDate, toDate), HttpStatus.OK);
    }
}
