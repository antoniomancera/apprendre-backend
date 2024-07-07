package com.antonio.apprendrebackend.mapper;

import com.antonio.apprendrebackend.dto.PhraseDTO;
import com.antonio.apprendrebackend.model.Phrase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhraseMapper {
    PhraseMapper INSTANCE = Mappers.getMapper(PhraseMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "phraseEs", source = "phraseEs")
    @Mapping(target = "phraseFr", source = "phraseFr")
    @Mapping(target = "description", source = "description")
    PhraseDTO toDTO(Phrase phrase);
}
