package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface WordTranslationMapper {

    WordTranslationMapper INSTANCE = Mappers.getMapper(WordTranslationMapper.class);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "wordFr", source = "wordFr")
    @Mapping(target = "wordSp", source = "wordSp")
    @Mapping(target = "attempts", source = "attempts")
    @Mapping(target = "successes", source = "successes")
    WordTranslationDTO toDTO(WordTranslation wordTranslation);


    @AfterMapping
    default void setRandomPhrase(WordTranslation wordTranslation, @MappingTarget WordTranslationDTO wordTranslationDTO) {
        PhraseMapper phraseMapper = Mappers.getMapper(PhraseMapper.class);
        if (wordTranslation.getRandomPhrase() != null) {
            wordTranslationDTO.setPhrase(phraseMapper.toDTO(wordTranslation.getRandomPhrase().getPhrase()));
        }
    }
}