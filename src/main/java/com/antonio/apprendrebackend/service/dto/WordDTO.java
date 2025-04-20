package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.Word;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordDTO {
    private LanguageDTO language;
    private String name;
}
