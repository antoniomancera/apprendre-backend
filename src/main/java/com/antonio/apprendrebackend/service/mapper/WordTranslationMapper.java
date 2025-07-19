package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface WordTranslationMapper {
    WordTranslationMapper INSTANCE = Mappers.getMapper(WordTranslationMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "wordSenseFr", source = "wordSenseFr")
    @Mapping(target = "wordSenseSp", source = "wordSenseSp")
    WordTranslationDTO toDTO(WordTranslation wordTranslation);
}