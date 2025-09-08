package com.antonio.apprendrebackend.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeckWithWordPhraseTranslationRequest {
    private String name;
    private String description;
    private List<Integer> wordPhraseTranslationIds;
}
