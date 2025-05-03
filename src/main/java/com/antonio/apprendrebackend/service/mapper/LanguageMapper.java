package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.LanguageDTO;
import com.antonio.apprendrebackend.service.model.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageMapper INSTANCE = Mappers.getMapper(LanguageMapper.class);

    @Mapping(target = "name", source = "name")
    LanguageDTO toDTO(Language language);
}

