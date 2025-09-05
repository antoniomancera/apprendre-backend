package com.antonio.apprendrebackend.service.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CourseDTO {
    private LanguageDTO baseLanguage;
    private LanguageDTO targetLanguage;
    private String code;
}
