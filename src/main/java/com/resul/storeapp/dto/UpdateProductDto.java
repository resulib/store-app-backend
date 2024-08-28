package com.resul.storeapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class UpdateProductDto {
    private Long categoryId;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private String description;
}
