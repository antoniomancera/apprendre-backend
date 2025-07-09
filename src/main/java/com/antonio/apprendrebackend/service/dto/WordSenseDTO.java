package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Category;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.Word;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSenseDTO {
    private Integer id;
    private WordDTO word;
    private String sense;
}
