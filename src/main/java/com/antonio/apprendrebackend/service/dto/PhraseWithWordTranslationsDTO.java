package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.WordTranslation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhraseWithWordTranslationsDTO {
    private PhraseDTO phrase;
    private List<WordTranslationDTO> wordTranslations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhraseWithWordTranslationsDTO that = (PhraseWithWordTranslationsDTO) o;
        return Objects.equals(phrase, that.phrase) &&
                Objects.equals(wordTranslations, that.wordTranslations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phrase, wordTranslations);
    }
}
