package com.ladyshopee.api.service;

import com.ladyshopee.api.payload.dto.ProductDTO;
import com.ladyshopee.api.payload.dto.TransactionDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    List<ProductDTO> getProducts(int page, int size);

    ProductDTO getProductByCode(String code);

    ProductDTO processTransaction(TransactionDTO transactionDTO);
}
