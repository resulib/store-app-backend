package com.resul.storeapp;

import com.resul.storeapp.entity.CategoryEntity;
import com.resul.storeapp.exception.CategoryNotFoundException;
import com.resul.storeapp.repository.CategoryRepository;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryManager {
    private final CategoryRepository categoryRepository;

    public CategoryEntity getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }
}
