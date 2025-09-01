package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Tense;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenseWithoutMoodDTO {
    private LanguageDTO language;
    private Tense.TenseEnum code;
    private String name;
}
