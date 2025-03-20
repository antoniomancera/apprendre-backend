package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordSenseSpDTO;
import com.antonio.apprendrebackend.service.model.WordSenseSp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WordSenseSpMapper {
    WordSenseSpMapper INSTANCE = Mappers.getMapper(WordSenseSpMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "wordSp", source = "wordSp")
    @Mapping(target = "sense", source = "sense")
    @Mapping(target = "type", source = "type")
    WordSenseSpDTO toDTO(WordSenseSp wordSense);
}


