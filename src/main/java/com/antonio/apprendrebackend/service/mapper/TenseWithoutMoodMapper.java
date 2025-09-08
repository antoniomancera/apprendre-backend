package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.LanguageDTO;
import com.antonio.apprendrebackend.service.dto.TenseWithoutMoodDTO;
import com.antonio.apprendrebackend.service.dto.WordSenseWithoutWordDTO;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.model.WordSense;
import jakarta.persistence.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TenseWithoutMoodMapper {
    TenseWithoutMoodMapper INSTANCE = Mappers.getMapper(TenseWithoutMoodMapper.class);

    @Mapping(target = "language", source = "language")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    TenseWithoutMoodDTO toDTO(Tense tense);
}
