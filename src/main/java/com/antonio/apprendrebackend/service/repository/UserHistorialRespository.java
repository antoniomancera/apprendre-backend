package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserHistorialRespository extends CrudRepository<UserHistorial, Integer> {
    /**
     * Get the DeckWord between two dates (start and end)
     *
     * @param start
     * @param end
     * @return
     */
    List<UserHistorial> findByUserInfoAndDateBetweenOrderByDateDesc(UserInfo userInfo, Long start, Long end);

    /**
     * Get the last DeckWordTranslationHistorial
     *
     * @return DeckWordTranslationHistorial
     */
    Optional<UserHistorial> findFirstByUserInfoOrderByDateDesc(UserInfo userInfo);

    /**
     * Return the UserHistorial of a word and an user
     *
     * @param userId
     * @param wordId
     * @return List<UserHistorial>
     */
    @Query(value = """
            SELECT DISTINCT uh
            FROM UserHistorial uh
            JOIN uh.deckWordPhraseTranslation dwpt
            JOIN dwpt.wordPhraseTranslation wpt
            JOIN wpt.wordTranslation wt
            JOIN wt.wordSenseA ws
            WHERE uh.userInfo.id = :userId
            AND ws.word.id = :wordId
            UNION
            SELECT DISTINCT uh
            FROM UserHistorial uh
            JOIN uh.deckWordPhraseTranslation dwpt
            JOIN dwpt.wordPhraseTranslation wpt
            JOIN wpt.wordTranslation wt
            JOIN wt.wordSenseB ws
            WHERE uh.userInfo.id = :userId
            AND ws.word.id = :wordId
            """)
    List<UserHistorial> findByUserInfoIdAndWordId(Integer userId, Integer wordId);

    /**
     * Return all the UserHistorials of a word in a deck
     *
     * @param deckId
     * @param wordId
     * @return List<UserHistorial>
     */
    @Query(value = """
            SELECT DISTINCT uh
            FROM UserHistorial uh
            JOIN uh.deckWordPhraseTranslation dwpt
            JOIN dwpt.wordPhraseTranslation wpt
            JOIN wpt.wordTranslation wt
            JOIN wt.wordSenseA ws
            WHERE dwpt.deck.id = :deckId
            AND ws.word.id = :wordId
            UNION
            SELECT DISTINCT uh
            FROM UserHistorial uh
            JOIN uh.deckWordPhraseTranslation dwpt
            JOIN dwpt.wordPhraseTranslation wpt
            JOIN wpt.wordTranslation wt
            JOIN wt.wordSenseB ws
            WHERE dwpt.deck.id = :deckId
            AND ws.word.id = :wordId
            """)
    List<UserHistorial> findByDeckIdAndWordId(Integer deckId, Integer wordId);

    /**
     * Return all the UserHistorials of a wordSense in a deck
     *
     * @param deckId
     * @param wordSenseId
     * @return List<UserHistorial>
     */
    @Query(value = """
            SELECT DISTINCT uh
            FROM UserHistorial uh
            JOIN uh.deckWordPhraseTranslation dwpt
            JOIN dwpt.wordPhraseTranslation wpt
            JOIN wpt.wordTranslation wt
            WHERE dwpt.deck.id = :deckId
            AND wt.wordSenseA.id = :wordSenseId
            UNION
            SELECT DISTINCT uh
            FROM UserHistorial uh
            JOIN uh.deckWordPhraseTranslation dwpt
            JOIN dwpt.wordPhraseTranslation wpt
            JOIN wpt.wordTranslation wt
            WHERE dwpt.deck.id = :deckId
            AND wt.wordSenseB.id = :wordSenseId
            """)
    List<UserHistorial> findByDeckIdAndWordSenseId(Integer deckId, Integer wordSenseId);
}

