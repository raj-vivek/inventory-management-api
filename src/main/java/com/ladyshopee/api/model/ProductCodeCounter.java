package com.ladyshopee.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product-code-counter")
@NoArgsConstructor
@Data
public class ProductCodeCounter {
    @Id
    private String counterName; // Use a more descriptive ID
    private int value;

    public ProductCodeCounter(String counterName) {
        this.counterName = counterName;
        this.value = 0;
    }

}
