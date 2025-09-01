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
public class MoodWithTenseDTO {
    private MoodDTO mood;
    private List<TenseWithoutMoodDTO> tenses;

    public MoodWithTenseDTO(List<TenseWithoutMoodDTO> tenses) {
        this.tenses = tenses;
    }
}
