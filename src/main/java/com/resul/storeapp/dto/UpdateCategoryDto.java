package com.resul.storeapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryDto {
    private String name;
    public boolean isActive;
}
