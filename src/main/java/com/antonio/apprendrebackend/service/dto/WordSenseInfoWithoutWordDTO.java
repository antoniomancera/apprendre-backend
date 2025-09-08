package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordSenseInfoWithoutWordDTO {
    private WordSenseWithoutWordDTO wordSense;
    private Integer attempts;
    private Double success;
    private List<CategoryDTO> categories;
}
