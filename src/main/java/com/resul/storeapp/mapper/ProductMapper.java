package com.resul.storeapp.mapper;

import com.resul.storeapp.dto.CreateProductDto;
import com.resul.storeapp.dto.ProductDto;
import com.resul.storeapp.dto.UpdateProductDto;
import com.resul.storeapp.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    List<ProductDto> toProductDtoList(List<ProductEntity> productEntities);
    ProductDto toProductDto(ProductEntity productEntity);
    ProductEntity toProductEntity(CreateProductDto createProductDto);
    void toProductEntity(UpdateProductDto updateProductDto, @MappingTarget ProductEntity productEntity);
}
