package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.LevelDTO;
import com.antonio.apprendrebackend.service.model.Level;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LevelMapper {
    LevelMapper INSTANCE = Mappers.getMapper(LevelMapper.class);

    @Mapping(target = "code", source = "code")
    LevelDTO toDTO(Level level);
}
