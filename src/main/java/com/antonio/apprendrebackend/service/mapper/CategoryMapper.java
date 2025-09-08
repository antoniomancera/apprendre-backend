package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.CategoryDTO;
import com.antonio.apprendrebackend.service.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "name", source = "name")
    CategoryDTO toDTO(Category category);
}
