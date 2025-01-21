package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.PhraseWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface PhraseService {
    List<Phrase> findPhrasesByWordTranslation(WordTranslation wordTranslation);

    List<PhraseWithWordTranslationsDTO> getAllPhrasesWithWordTranslationsByDeck(Integer deckId);
}
