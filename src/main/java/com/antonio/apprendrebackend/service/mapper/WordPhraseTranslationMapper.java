package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {PhraseTranslationMapper.class})
public interface WordPhraseTranslationMapper {
    WordPhraseTranslationMapper INSTANCE = Mappers.getMapper(WordPhraseTranslationMapper.class);
    
    @Mapping(target = "id", source = "wordPhraseTranslation.id")
    @Mapping(target = "wordTranslation", source = "wordPhraseTranslation.wordTranslation", qualifiedByName = "mapWordTranslation")
    @Mapping(target = "phraseTranslation", source = "wordPhraseTranslation.phraseTranslation", qualifiedByName = "mapPhraseTranslation")
    WordPhraseTranslationDTO toDTO(WordPhraseTranslation wordPhraseTranslation, @Context Language courseLanguage);

    @Named("mapWordTranslation")
    default WordTranslationDTO mapWordTranslation(WordTranslation wordTranslation, @Context Language courseLanguage) {
        return WordTranslationMapper.INSTANCE.toDTO(wordTranslation, courseLanguage);
    }

    @Named("mapPhraseTranslation")
    default PhraseTranslationDTO mapPhraseTranslation(PhraseTranslation phraseTranslation, @Context Language courseLanguage) {
        return PhraseTranslationMapper.INSTANCE.toDTO(phraseTranslation, courseLanguage);
    }
}