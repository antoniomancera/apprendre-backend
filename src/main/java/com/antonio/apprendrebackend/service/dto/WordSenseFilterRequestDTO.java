package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Number;
import com.antonio.apprendrebackend.service.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordSenseFilterRequestDTO {
    private int minAccuracy;
    private int maxAccuracy;
    private List<Category> categories;
    private List<Person> persons;
    private List<Gender> genders;
    private List<Number> numbers;
    private List<Tense> tenses;

    public boolean hasAnyFilter() {
        return this.getMinAccuracy() > 0 || this.getMaxAccuracy() < 100
                || this.getCategories() != null && this.getCategories().size() > 0
                || this.getPersons() != null && this.getPersons().size() > 0
                || this.getGenders() != null && this.getNumbers().size() > 0
                || this.getNumbers() != null && this.getNumbers().size() > 0
                || this.getTenses() != null && this.getTenses().size() > 0;
    }
}
