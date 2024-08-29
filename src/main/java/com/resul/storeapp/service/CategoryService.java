package com.resul.storeapp.service;

import com.resul.storeapp.CategoryManager;
import com.resul.storeapp.dto.CategoryDto;
import com.resul.storeapp.dto.CreateCategoryDto;
import com.resul.storeapp.dto.UpdateCategoryDto;
import com.resul.storeapp.entity.CategoryEntity;
import com.resul.storeapp.exception.CategoryNotFoundException;
import com.resul.storeapp.mapper.CategoryMapper;
import com.resul.storeapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryManager categoryManager;

    public List<CategoryDto> findAll() {
        var categories = categoryRepository.findAllAndIsActive(true);
        return categoryMapper.toDtoList(categories);
    }

    public CategoryDto findById(Long id) {
        var category = categoryManager.getCategory(id);
        return categoryMapper.toDto(category);
    }

    public void create(CreateCategoryDto createCategoryDto) {
        var categoryEntity = categoryMapper.toEntity(createCategoryDto);
        categoryEntity.setActive(true);
        categoryRepository.save(categoryEntity);
    }

    public void update(Long id, UpdateCategoryDto updateCategoryDto) {
        var categoryEntity = categoryManager.getCategory(id);
        categoryMapper.toCategoryEntity(updateCategoryDto, categoryEntity);
        categoryRepository.save(categoryEntity);
    }

    public void delete(Long id) {
        var categoryEntity = categoryManager.getCategory(id);
        categoryRepository.delete(categoryEntity);
    }

}
