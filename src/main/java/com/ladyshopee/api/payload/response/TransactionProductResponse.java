package com.ladyshopee.api.payload.response;

import com.ladyshopee.api.payload.dto.ProductDTO;
import com.ladyshopee.api.payload.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionProductResponse {
    TransactionDTO transaction;
    ProductDTO product;
}
