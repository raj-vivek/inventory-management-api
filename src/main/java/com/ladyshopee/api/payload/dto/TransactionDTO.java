package com.ladyshopee.api.payload.dto;

import com.ladyshopee.api.model.ColorAndSizes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private String id;
    private String productCode;
    private Integer sellPrice;
    private Integer units;
    private String desc;
    private String phoneNo;
    private ColorAndSizes colorAndSizes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
