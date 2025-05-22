package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordDTO {
    private LanguageDTO language;
    private String name;
    private Type type;
}
