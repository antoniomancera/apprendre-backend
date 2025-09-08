package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.MoodDTO;
import com.antonio.apprendrebackend.service.dto.PartSpeechDTO;
import com.antonio.apprendrebackend.service.model.Mood;
import com.antonio.apprendrebackend.service.model.PartSpeech;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartSpeechMapper {
    PartSpeechMapper INSTANCE = Mappers.getMapper(PartSpeechMapper.class);

    @Mapping(target = "code", source = "code")
    PartSpeechDTO toDTO(PartSpeech partSpeech);
}
