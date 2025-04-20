package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.DeckUserWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.service.DeckUserWordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordPhraseTranslationServiceServiceImpl implements WordPhraseTranslationService {

    @Autowired
    DeckUserWordPhraseTranslationService deckUserWordPhraseTranslationService;

    @Autowired
    WordPhraseTranslationMapper wordPhraseTranslationMapper;

    /**
     * Return a Random WordTranslation depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    @Override
    public WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId) {
        DeckUserWordPhraseTranslation deckWordTranslation;
        if (deckId != null) {
            deckWordTranslation = deckUserWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(userInfo.getId(), deckId);
        } else {
            deckWordTranslation = deckUserWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId());
        }
        return wordPhraseTranslationMapper.toDTO(deckWordTranslation.getWordPhraseTranslation());
    }

    @Override
    public WordTranslationDTO attemptsWordPhraseTranslation(int wordId, int phraseId, boolean success, Integer deckId) {
        return null;
        /*
        WordTranslation wordTranslation = wordTranslationRepository.findById(wordId)
                .orElseThrow(() -> new WordTranslationNotFoundException("WordTranslation not found"));
        Phrase phrase = phraseTranslationRepository.findById(phraseId)
                .orElseThrow(() -> new WordTranslationNotFoundException("Phrase not found"));

        updateStats(success, wordTranslation, phrase);
        userHistorialRespository.save(new UserHistorial(wordTranslation, LocalDate.now().atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli(), success ? 1 : 0, deckId));

        return getRandomWordTranslation(deckId);

         */
    }

    private static void updateStats(boolean success, WordTranslation wordTranslation, Phrase phrase) {
        /*
        wordTranslation.setAttempts(wordTranslation.getAttempts() + 1);
        phrase.setAttempts(phrase.getAttempts() + 1);
        if (success) {
            wordTranslation.setSuccesses(wordTranslation.getSuccesses() + 1);
            phrase.setSuccesses(phrase.getSuccesses() + 1);
        }

         */
    }

}
