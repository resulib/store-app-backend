package com.resul.storeapp.controller;

import com.resul.storeapp.dto.CreateProductDto;
import com.resul.storeapp.dto.ProductDto;
import com.resul.storeapp.dto.UpdateProductDto;
import com.resul.storeapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

//    @GetMapping
//    public ResponseEntity<List<ProductDto>> findAll() {
//        return ResponseEntity.ok().body(productService.findAll());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ProductDto>> findAllByCategory(@RequestParam(required = false) Long category) {
//        return ResponseEntity.ok().body(productService.findAllByCategory(category));
//    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll(@RequestParam(required = false) Long category) {
        if (category != null) {
            return ResponseEntity.ok().body(productService.findAllByCategory(category));
        } else {
            return ResponseEntity.ok().body(productService.findAll());
        }
    }

        @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> findAll(@Param("query") String query) {
        return ResponseEntity.ok().body(productService.search(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateProductDto createProductDto) {
        productService.create(createProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateProductDto updateProductDto) {
        productService.update(id, updateProductDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
