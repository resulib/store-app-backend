package com.resul.storeapp.controller;

import com.resul.storeapp.dto.CreateProductDto;
import com.resul.storeapp.dto.ProductDto;
import com.resul.storeapp.dto.UpdateProductDto;
import com.resul.storeapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.resul.storeapp.constant.Constant.PHOTO_DIRECTORY;
import static org.springframework.util.MimeTypeUtils.IMAGE_JPEG_VALUE;
import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;

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

    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(productService.uploadPhoto(id, file));
    }


    @GetMapping(value = "/image/{filename}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable("filename") String filename) throws IOException {
        Path imagePath = Paths.get(PHOTO_DIRECTORY, filename);

        if (!Files.exists(imagePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);
        String contentType = Files.probeContentType(imagePath);  // Dynamically determine the content type

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(imageBytes);
    }

}
