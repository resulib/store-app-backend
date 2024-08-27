package com.resul.storeapp.mapper;

import com.resul.storeapp.dto.CategoryDto;
import com.resul.storeapp.dto.CreateCategoryDto;
import com.resul.storeapp.dto.UpdateCategoryDto;
import com.resul.storeapp.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(CategoryEntity categoryEntity);
    List<CategoryDto> toDtoList(List<CategoryEntity> categoryEntityList);
    CategoryEntity toEntity(CreateCategoryDto createCategoryDto);
    void toCategoryEntity(UpdateCategoryDto updateCategoryDto, @MappingTarget CategoryEntity categoryEntity);


}
