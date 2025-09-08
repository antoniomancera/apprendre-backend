package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.WordSenseDTO;
import com.antonio.apprendrebackend.service.dto.WordSenseWithoutWordDTO;
import com.antonio.apprendrebackend.service.model.WordSense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WordSenseWithoutWordMapper {
    WordSenseWithoutWordMapper INSTANCE = Mappers.getMapper(WordSenseWithoutWordMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "sense", source = "sense")
    WordSenseWithoutWordDTO toDTO(WordSense wordSense);
}
