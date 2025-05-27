package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordSenseDTO;
import com.antonio.apprendrebackend.service.model.WordSense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WordSenseMapper {
    WordSenseMapper INSTANCE = Mappers.getMapper(WordSenseMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "word", source = "word")
    @Mapping(target = "sense", source = "sense")
    WordSenseDTO toDTO(WordSense wordSense);
}


