package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.PhraseDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhraseMapper {
    PhraseMapper INSTANCE = Mappers.getMapper(PhraseMapper.class);
    
    @Mapping(target = "phrase", source = "phrase")
    @Mapping(target = "language", source = "language")
    PhraseDTO toDTO(Phrase phrase);
}
