package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordSenseFrDTO;
import com.antonio.apprendrebackend.service.model.WordSenseFr;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WordSenseFrMapper {
    WordSenseFrMapper INSTANCE = Mappers.getMapper(WordSenseFrMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "wordFr", source = "wordFr")
    @Mapping(target = "sense", source = "sense")
    @Mapping(target = "type", source = "type")
    WordSenseFrDTO toDTO(WordSenseFr wordSense);
}
