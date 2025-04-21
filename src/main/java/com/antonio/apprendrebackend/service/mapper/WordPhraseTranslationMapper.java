package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.WordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {PhraseTranslationMapper.class})
public interface WordPhraseTranslationMapper {
    WordPhraseTranslationMapper INSTANCE = Mappers.getMapper(WordPhraseTranslationMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "wordTranslation", source = "wordTranslation")
    @Mapping(target = "phraseTranslation", source = "phraseTranslation")
    WordPhraseTranslationDTO toDTO(WordPhraseTranslation wordPhraseTranslation);
}