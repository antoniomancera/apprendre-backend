package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.UserHistorialDTO;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserHistorialMapper {

    @Mapping(target = "deckWordPhraseTranslation", source = "deckWordPhraseTranslation")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "success", source = "success")
    @Mapping(target = "deck", source = "deck")
    @Mapping(target = "attempts", ignore = true)
    UserHistorialDTO toDTO(UserHistorial userHistorial);
}
