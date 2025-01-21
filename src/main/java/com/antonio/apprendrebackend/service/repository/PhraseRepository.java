package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Phrase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhraseRepository extends CrudRepository<Phrase, Integer> {
    @Query("SELECT p FROM Phrase p JOIN WordTranslationPhrase wtp ON p.id = wtp.phrase.id WHERE wtp.wordTranslation.id = :wordTranslationId")
    List<Phrase> findPhrasesByWordTranslationId(@Param("wordTranslationId") Integer wordTranslationId);

    @Query("""
                SELECT p
                FROM Phrase p
                JOIN WordTranslationPhrase wtp ON p.id = wtp.phrase.id
                JOIN DeckWordTranslation dwt ON wtp.wordTranslation.id = dwt.wordTranslation.id
                WHERE dwt.deck.id = :deckId
            """)
    Optional<List<Phrase>> findPhrasesByDeckId(@Param("deckId") Integer deckId);


    //List<Phrase> findByWordTranslationPhrase_WordTranslation_DeckWordTranslation_Deck_Id(Integer deckId);

}
