package com.resul.storeapp.service;

import com.resul.storeapp.manager.CategoryManager;
import com.resul.storeapp.dto.CategoryDto;
import com.resul.storeapp.dto.CreateCategoryDto;
import com.resul.storeapp.dto.UpdateCategoryDto;
import com.resul.storeapp.mapper.CategoryMapper;
import com.resul.storeapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryManager categoryManager;

    @Cacheable(value = "categories")
    public List<CategoryDto> findAll() {
        var categories = categoryRepository.findAllAndIsActive(true);
        return categoryMapper.toDtoList(categories);
    }

    @Cacheable(value = "categories")
    public CategoryDto findById(Long id) {
        var category = categoryManager.getCategory(id);
        return categoryMapper.toDto(category);
    }

    public void create(CreateCategoryDto createCategoryDto) {
        var categoryEntity = categoryMapper.toEntity(createCategoryDto);
        categoryEntity.setActive(true);
        categoryRepository.save(categoryEntity);
    }

    @CacheEvict(value = "categories", allEntries = true)
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
