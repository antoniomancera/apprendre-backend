package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.WordSp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSenseSpDTO {
    private Integer id;
    private WordSp wordSp;
    private String sense;
    private Type type;
}
