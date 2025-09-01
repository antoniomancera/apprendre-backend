package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.TenseDTO;
import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.model.Word;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TenseMapper {
    WordMapper INSTANCE = Mappers.getMapper(WordMapper.class);

    @Mapping(target = "mood", source = "mood")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    TenseDTO toDTO(Tense tense);
}


