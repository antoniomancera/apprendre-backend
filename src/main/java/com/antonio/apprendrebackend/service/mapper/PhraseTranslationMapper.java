package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhraseTranslationMapper {
    PhraseTranslationMapper INSTANCE = Mappers.getMapper(PhraseTranslationMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "phraseSp", source = "phraseSp.phrase")
    @Mapping(target = "phraseFr", source = "phraseFr.phrase")
    PhraseTranslationDTO toDTO(PhraseTranslation phraseTranslation);


}
