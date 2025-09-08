package com.antonio.apprendrebackend.service.dto;


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
    private WordSenseDTO wordSenseTarget;
    private WordSenseDTO wordSenseBase;
}
