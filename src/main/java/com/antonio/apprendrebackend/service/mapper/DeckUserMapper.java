package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.DeckUserDTO;
import com.antonio.apprendrebackend.service.model.DeckUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeckUserMapper {
    DeckUserMapper INSTANCE = Mappers.getMapper(DeckUserMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    DeckUserDTO toDTO(DeckUser deckUser);
}
