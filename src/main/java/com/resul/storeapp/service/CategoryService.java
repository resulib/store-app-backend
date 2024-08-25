package com.resul.storeapp.service;

import com.resul.storeapp.entity.CategoryEntity;
import com.resul.storeapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    public void create(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
    }
}
