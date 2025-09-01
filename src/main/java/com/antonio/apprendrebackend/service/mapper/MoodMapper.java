package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.MoodDTO;
import com.antonio.apprendrebackend.service.model.Mood;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MoodMapper {
    WordMapper INSTANCE = Mappers.getMapper(WordMapper.class);

    @Mapping(target = "code", source = "code")
    MoodDTO toDTO(Mood mood);
}


