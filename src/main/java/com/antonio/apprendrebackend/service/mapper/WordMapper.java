package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.dto.WordSenseDTO;
import com.antonio.apprendrebackend.service.model.Word;
import com.antonio.apprendrebackend.service.model.WordSense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WordMapper {
    WordMapper INSTANCE = Mappers.getMapper(WordMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "partSpeech", source = "partSpeech")
    WordDTO toDTO(Word word);
}


