package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.model.Number;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordFilterOptionsDTO {
    private List<PartSpeech> partSpeeches;
    private List<Level> levels;
    private List<Category> categories;
    private List<Person> persons;
    private List<Gender> genders;
    private List<Number> numbers;
    private List<MoodWithTenseDTO> moodWithTenses;
}
