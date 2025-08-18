package com.antonio.apprendrebackend.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordDTO {
    private Integer id;
    private LanguageDTO language;
    private String name;
    private PartSpeechDTO partSpeech;
    private LevelDTO level;
}
