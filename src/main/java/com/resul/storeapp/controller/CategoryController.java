package com.resul.storeapp.controller;

import com.resul.storeapp.entity.CategoryEntity;
import com.resul.storeapp.repository.CategoryRepository;
import com.resul.storeapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
        categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
