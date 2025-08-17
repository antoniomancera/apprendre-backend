package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.PartSpeech;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordDTO {
    private Integer id;
    private LanguageDTO language;
    private String name;
    private PartSpeech partSpeech;
}
