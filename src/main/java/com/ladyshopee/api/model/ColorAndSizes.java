package com.ladyshopee.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColorAndSizes {
    private String color;
    private Map<Size, Integer> sizes;
}
