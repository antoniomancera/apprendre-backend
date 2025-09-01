package com.antonio.apprendrebackend.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSenseDTO {
    private Integer id;
    private WordDTO word;
    private String sense;
}
