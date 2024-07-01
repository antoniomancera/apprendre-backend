package com.antonio.apprendrebackend.mapper;

import com.antonio.apprendrebackend.dto.MotPalabraDTO;
import com.antonio.apprendrebackend.model.MotPalabra;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public interface MotPalabraMapper {

    MotPalabraMapper INSTANCE = Mappers.getMapper(MotPalabraMapper.class);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "mot", source = "mot")
    @Mapping(target = "palabra", source = "palabra")
    @Mapping(target = "attempts", source = "attempts")
    @Mapping(target = "successes", source = "successes")
    MotPalabraDTO toDTO(MotPalabra motPalabra);


    @AfterMapping
    default void setRandomPhrase(MotPalabra motPalabra, @MappingTarget MotPalabraDTO motPalabraDTO) {
        PhraseMapper phraseMapper = Mappers.getMapper(PhraseMapper.class);
        if (motPalabra.getRandomPhrase() != null) {
            motPalabraDTO.setPhrase(phraseMapper.toDTO(motPalabra.getRandomPhrase().getPhrase()));
        }
    }
}