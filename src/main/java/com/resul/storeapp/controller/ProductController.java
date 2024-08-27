package com.resul.storeapp.controller;

import com.resul.storeapp.entity.CategoryEntity;
import com.resul.storeapp.entity.ProductEntity;
import com.resul.storeapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllCategories() {
        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getAllCategories(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createCategory(@RequestBody ProductEntity productEntity) {
        productService.create(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
