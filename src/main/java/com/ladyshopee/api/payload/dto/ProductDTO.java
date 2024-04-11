package com.ladyshopee.api.payload.dto;

import com.ladyshopee.api.model.ColorAndSizes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private String id;
    private String name;
    private String code;
    private String desc;
    private Integer buyPrice;
    private Integer mrp;
    private List<ColorAndSizes> colorAndSizes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

