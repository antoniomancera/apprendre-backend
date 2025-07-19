package com.antonio.apprendrebackend.service.dto;


import com.antonio.apprendrebackend.service.model.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordTranslationDTO {
    private Integer id;
    private WordSenseDTO wordSenseFr;
    private WordSenseDTO wordSenseSp;
}
