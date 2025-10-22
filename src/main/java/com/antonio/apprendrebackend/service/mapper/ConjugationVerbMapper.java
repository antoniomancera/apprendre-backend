package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.ConjugationVerbDTO;
import com.antonio.apprendrebackend.service.model.ConjugationVerb;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConjugationVerbMapper {
    ConjugationVerbMapper INSTANCE = Mappers.getMapper(ConjugationVerbMapper.class);

    @Mapping(target = "verbAuxiliaryName", source = "verbAuxiliary.name")
    @Mapping(target = "verbGroupEnum", source = "verbGroupEnding.verbGroup.verbGroupEnum")
    ConjugationVerbDTO toDTO(ConjugationVerb conjugationVerb);
}
