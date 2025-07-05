package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Mood;
import com.antonio.apprendrebackend.service.model.Tense;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenseDTO {
    private Mood mood;
    private LanguageDTO language;
    private Tense.TenseEnum tenseEnum;
    private String name;
}
