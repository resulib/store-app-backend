package com.resul.storeapp.service;

import com.resul.storeapp.entity.ProductEntity;
import com.resul.storeapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public ProductEntity findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void create(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }
}
