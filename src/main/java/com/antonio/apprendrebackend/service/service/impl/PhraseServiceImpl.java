package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.PhraseWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.PhraseRepository;
import com.antonio.apprendrebackend.service.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhraseServiceImpl implements PhraseService {
    @Autowired
    PhraseRepository phraseRepository;

    @Autowired
    WordTranslationRepository wordTranslationRepository;

    @Autowired
    WordTranslationMapper wordTranslationMapper;

    @Autowired
    PhraseMapper phraseMapper;

    @Override
    public List<Phrase> findPhrasesByWordTranslation(WordTranslation wordTranslation) {
        return phraseRepository.findPhrasesByWordTranslationId(wordTranslation.getId());
    }

    @Override
    public List<PhraseWithWordTranslationsDTO> getAllPhrasesWithWordTranslationsByDeck(Integer deckId) {
        Optional<List<Phrase>> phrasesOptional = phraseRepository.findPhrasesByDeckId(deckId);

        if (phrasesOptional.isEmpty() || phrasesOptional.get().size() == 0) {
            throw new PhraseNotFoundException(String.format("Not found any phrase of deck %s", deckId));
        }

        List<Phrase> phrases = phrasesOptional.get();
        //List<WordTranslation> wordTranslations = wordTranslationRepository.findWordTranslationsByPhraseIdAndDeckId(phrases.get(0).getId(), deckId);
        //System.out.println(phrases.get(0).toString());
        //System.out.println(wordTranslations.get(0).toString());
        return phrases.stream().map(phrase -> {
            List<WordTranslation> wordTranslations = wordTranslationRepository.findWordTranslationsByPhraseIdAndDeckId(phrase.getId(), deckId);
            List<WordTranslationDTO> wordTranslationDTOs = wordTranslations.stream()
                    .map(wordTranslationMapper::toDTO)
                    .collect(Collectors.toList());

            return new PhraseWithWordTranslationsDTO(
                    phraseMapper.toDTO(phrase),
                    wordTranslationDTOs
            );
        }).collect(Collectors.toList());
        /*
        List<Phrase> phrases = phraseRepository.findPhrasesByDeckId(deckId);
        return phrases.stream().map(phrase -> {
            List<WordTranslation> wordTranslations = wordTranslationRepository.findWordTranslationsByPhraseIdAndDeckId(phrase.getId(), deckId);
            List<WordTranslationDTO> wordTranslationDTOs = wordTranslations.stream()
                    .map(wordTranslationMapper::toDTO)
                    .collect(Collectors.toList());

            return new PhraseWithWordTranslationsDTO(
                    phraseMapper.toDTO(phrase),
                    wordTranslationDTOs
            );
        }).collect(Collectors.toList());

         */
    }
}
