package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.WordFr;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSenseFrDTO {
    private Integer id;
    private WordFr wordFr;
    private String sense;
    private Type type;
}
