package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.SuccessDTO;
import com.antonio.apprendrebackend.service.model.Success;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SuccessMapper {

    @Mapping(target = "successEnum", source = "successEnum")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "score", source = "score")
    SuccessDTO toDTO(Success success);
}
