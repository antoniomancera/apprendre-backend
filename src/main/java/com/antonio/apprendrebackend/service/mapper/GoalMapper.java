package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.dto.PhraseDTO;
import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.model.Phrase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    GoalMapper INSTANCE = Mappers.getMapper(GoalMapper.class);
    
    @Mapping(target = "attempts", source = "attempts")
    @Mapping(target = "successesAccuracy", source = "successesAccuracy")
    GoalDTO toDTO(Goal goal);
}

