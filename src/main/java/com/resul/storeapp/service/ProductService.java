package com.resul.storeapp.service;

import com.resul.storeapp.CategoryManager;
import com.resul.storeapp.dto.CreateProductDto;
import com.resul.storeapp.dto.ProductDto;
import com.resul.storeapp.dto.UpdateProductDto;
import com.resul.storeapp.entity.ProductEntity;
import com.resul.storeapp.exception.ProductNotFoundException;
import com.resul.storeapp.mapper.ProductMapper;
import com.resul.storeapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryManager categoryManager;

    public List<ProductDto> findAll() {
        var products = productRepository.findAll();
        return productMapper.toProductDtoList(products);
    }

    public ProductDto findById(Long id) {
        var productEntity = getProduct(id);
        return productMapper.toProductDto(productEntity);
    }

    public void create(CreateProductDto createProductDto) {
        var productEntity = productMapper.toProductEntity(createProductDto);
        var category = categoryManager.getCategory(createProductDto.getCategoryId());
        productEntity.setCategory(category);
        productRepository.save(productEntity);
    }

    public void update(Long id, UpdateProductDto updateProductDto) {
        var productEntity = getProduct(id);
        var category = categoryManager.getCategory(updateProductDto.getCategoryId());
        productEntity.setCategory(category);
        productMapper.toProductEntity(updateProductDto, productEntity);
        productRepository.save(productEntity);
    }

    public void delete(Long id) {
        var productEntity = getProduct(id);
        productEntity.setDeleted(true);
        productRepository.save(productEntity);
    }

    private ProductEntity getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public List<ProductDto> findAllByCategory(Long category) {
        var products = productRepository.findAllByCategoryId(category);
        return productMapper.toProductDtoList(products);
    }

    public List<ProductDto> search(String query) {
        var products = productRepository.search(query);
        return productMapper.toProductDtoList(products);
    }
}
