package com.ladyshopee.api.service.impl;

import com.ladyshopee.api.model.Product;
import com.ladyshopee.api.model.ProductCodeCounter;
import com.ladyshopee.api.payload.dto.ProductDTO;
import com.ladyshopee.api.payload.dto.TransactionDTO;
import com.ladyshopee.api.repository.CodeCounterRepository;
import com.ladyshopee.api.repository.ProductRepository;
import com.ladyshopee.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CodeCounterRepository codeCounterRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        int newCounter = getNextCounterValue();
        String newHexadecimalCode = generateHexadecimalCode(newCounter);

        Product product = Product.builder()
                .name(productDTO.getName())
                .desc(productDTO.getDesc())
                .buyPrice(productDTO.getBuyPrice())
                .mrp(productDTO.getMrp())
                .colorAndSizes(productDTO.getColorAndSizes())
                .code(newHexadecimalCode)
                .build();

        Product savedProduct = productRepository.save(product);
        return productToProductDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::productToProductDTO).toList();
    }

    @Override
    public ProductDTO getProductByCode(String code) {
        Product product = productRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Product not found"));
        return productToProductDTO(product);
    }

    @Override
    public ProductDTO processTransaction(TransactionDTO transactionDTO) {
        Product product = productRepository.findByCode(transactionDTO.getProductCode()).orElseThrow(()-> new RuntimeException("Product not found"));
        product.getColorAndSizes().stream()
                .filter(colorAndSizes -> colorAndSizes.getColor().equals(transactionDTO.getColorAndSizes().getColor()))
                .forEach(colorAndSizes -> colorAndSizes.getSizes().entrySet().stream()
                        .forEach(entry -> entry.setValue(entry.getValue() - transactionDTO.getColorAndSizes().getSizes().get(entry.getKey()))));
        Product savedProduct = productRepository.save(product);
        return productToProductDTO(savedProduct);
    }

    private synchronized int getNextCounterValue() {
        // Find the counter document, increment the value, and save it
        ProductCodeCounter counter = codeCounterRepository.findById("productCounter").orElse(new ProductCodeCounter("productCounter"));
        int newCounter = counter.getValue() + 1;
        counter.setValue(newCounter);
        codeCounterRepository.save(counter);
        return counter.getValue();
    }

    private String generateHexadecimalCode(int counterValue) {
        // Convert the counter value to a hexadecimal code
        String hexCode = Integer.toHexString(counterValue).toUpperCase();

        // Ensure the hexadecimal code is 4 characters long by padding with zeros if needed
        return String.format("%4s", hexCode).replace(' ', '0');
    }

    private ProductDTO productToProductDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .desc(product.getDesc())
                .buyPrice(product.getBuyPrice())
                .mrp(product.getMrp())
                .colorAndSizes(product.getColorAndSizes())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

}
