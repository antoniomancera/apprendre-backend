package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.PhraseDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhraseTranslationMapper {
    PhraseTranslationMapper INSTANCE = Mappers.getMapper(PhraseTranslationMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "phraseTarget", source = "phraseTranslation", qualifiedByName = "mapPhraseTarget")
    @Mapping(target = "phraseBase", source = "phraseTranslation", qualifiedByName = "mapPhraseBase")
    PhraseTranslationDTO toDTO(PhraseTranslation phraseTranslation, @Context Language courseLanguage);

    @Named("mapPhraseTarget")
    default PhraseDTO mapPhraseTarget(PhraseTranslation phraseTranslation, @Context Language courseLanguage) {
        if (phraseTranslation.getPhraseA().getLanguage().getCode().equals(courseLanguage.getCode())) {
            return PhraseMapper.INSTANCE.toDTO(phraseTranslation.getPhraseA());
        } else {
            return PhraseMapper.INSTANCE.toDTO(phraseTranslation.getPhraseB());
        }
    }

    @Named("mapPhraseBase")
    default PhraseDTO mapPhraseBase(PhraseTranslation phraseTranslation, @Context Language courseLanguage) {
        if (phraseTranslation.getPhraseA().getLanguage().getCode().equals(courseLanguage.getCode())) {
            return PhraseMapper.INSTANCE.toDTO(phraseTranslation.getPhraseB());
        } else {
            return PhraseMapper.INSTANCE.toDTO(phraseTranslation.getPhraseA());
        }
    }
}

