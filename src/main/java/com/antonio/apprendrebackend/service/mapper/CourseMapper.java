package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.CategoryDTO;
import com.antonio.apprendrebackend.service.dto.CourseDTO;
import com.antonio.apprendrebackend.service.model.Category;
import com.antonio.apprendrebackend.service.model.Course;
import com.antonio.apprendrebackend.service.model.Language;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "baseLanguage", source = "baseLanguage")
    @Mapping(target = "targetLanguage", source = "targetLanguage")
    @Mapping(target = "code", source = "code")
    CourseDTO toDTO(Course course);
}
