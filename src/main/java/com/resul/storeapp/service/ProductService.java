package com.resul.storeapp.service;

import com.resul.storeapp.manager.CategoryManager;
import com.resul.storeapp.dto.CreateProductDto;
import com.resul.storeapp.dto.ProductDto;
import com.resul.storeapp.dto.UpdateProductDto;
import com.resul.storeapp.entity.ProductEntity;
import com.resul.storeapp.exception.ProductNotFoundException;
import com.resul.storeapp.mapper.ProductMapper;
import com.resul.storeapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.resul.storeapp.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
@Slf4j
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


    public String uploadPhoto(Long id, MultipartFile file) {
        log.info("Saving picture for product ID: {}", id);
        ProductEntity productEntity = getProduct(id);
        String photoUrl = photoFunction.apply(id, file);
        productEntity.setImageUrl(photoUrl);
        productRepository.save(productEntity);
        return photoUrl;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<Long, MultipartFile, String> photoFunction = (id, image) -> {
        String filename = id  + UUID.randomUUID().toString() + fileExtension.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/products/image/" + filename).toUriString();
        } catch (Exception exception) {
            throw new RuntimeException("Unable to save image");
        }
    };

}
