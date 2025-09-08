package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordSenseDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface WordTranslationMapper {
    WordTranslationMapper INSTANCE = Mappers.getMapper(WordTranslationMapper.class);

    @Mapping(target = "id", source = "wordTranslation.id")
    @Mapping(target = "wordSenseTarget", source = "wordTranslation", qualifiedByName = "mapWordSenseTarget")
    @Mapping(target = "wordSenseBase", source = "wordTranslation", qualifiedByName = "mapWordSenseBase")
    WordTranslationDTO toDTO(WordTranslation wordTranslation, @Context Language courseLanguage);

    @Named("mapWordSenseTarget")
    default WordSenseDTO mapWordSenseTarget(WordTranslation wordTranslation, @Context Language courseLanguage) {
        if (wordTranslation.getWordSenseA().getWord().getLanguage().getCode().equals(courseLanguage.getCode())) {
            return WordSenseMapper.INSTANCE.toDTO(wordTranslation.getWordSenseA());
        } else {
            return WordSenseMapper.INSTANCE.toDTO(wordTranslation.getWordSenseB());
        }
    }

    @Named("mapWordSenseBase")
    default WordSenseDTO mapWordSenseBase(WordTranslation wordTranslation, @Context Language courseLanguage) {
        if (wordTranslation.getWordSenseA().getWord().getLanguage().getCode().equals(courseLanguage.getCode())) {
            return WordSenseMapper.INSTANCE.toDTO(wordTranslation.getWordSenseB());
        } else {
            return WordSenseMapper.INSTANCE.toDTO(wordTranslation.getWordSenseA());
        }
    }
}

