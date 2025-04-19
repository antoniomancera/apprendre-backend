package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.UserHistorialDTO;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserHistorialMapper {

    @Mapping(target = "wordTranslation", source = "wordTranslation")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "success", source = "success")
    @Mapping(target = "deckId", source = "deckId")
    @Mapping(target = "attempts", ignore = true)
    UserHistorialDTO toDTO(UserHistorial userHistorial);
}
