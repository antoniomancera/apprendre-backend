package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.DeckAlreadyExistsException;
import com.antonio.apprendrebackend.service.exception.DeckNotFoundException;
import com.antonio.apprendrebackend.service.mapper.DeckMapper;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.DeckRepository;
import com.antonio.apprendrebackend.service.service.DeckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.antonio.apprendrebackend.service.util.GeneralConstants.MAX_DECKS;

@Service
public class DeckServiceImpl implements DeckService {
    private static final Logger logger = LoggerFactory.getLogger(DeckServiceImpl.class);

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private DeckMapper deckMapper;

    /**
     * Get all active decks by user
     *
     * @return List<Deck>
     */
    @Override
    public List<Deck> getActiveDecks(UserInfo userInfo) {
        logger.debug("Calle getActiveDecks in DeckService for user-{}", userInfo);

        return deckRepository.findByEndDateNullAndUserInfo(userInfo);
    }

    /**
     * Get a deck by their id
     *
     * @param deckId
     * @return Deck
     * @throws DeckNotFoundException if not found anyone with their id
     */
    @Override
    public Deck getDeckbyId(Integer deckId) {
        logger.debug("Called getDeckbyId in DeckService for deck-{}", deckId);

        return deckRepository.findById(deckId).orElseThrow(() -> new DeckNotFoundException(String.format("Not found any deck with id: %s", deckId)));
    }

    /**
     * Create a new Deck
     *
     * @param deck
     * @return Deck
     * @throws DeckAlreadyExistsException if exists a Deck with the same name
     */
    @Override
    public Deck createDeck(Deck deck) {
        logger.debug("Calle createDeck in DeckService for deck-{}", deck);

        List<Deck> sameNameDecks = deckRepository.findByName(deck.getName());
        if (sameNameDecks != null && sameNameDecks.size() > 0) {
            throw new DeckAlreadyExistsException(String.format("A deck with name: %s already exists", deck.getName()));
        }

        return deckRepository.save(deck);
    }

    /**
     * Get if the user has reached the  of decks already in use
     *
     * @return Boolean
     */
    @Override
    public Boolean isDeckLimitNotReached(Integer userId) {
        logger.debug("Calle createDeck in isDeckLimitNotReached for user-{}", userId);

        return deckRepository.countByUserIdAndEndDateNull(userId) < MAX_DECKS;
    }

    /**
     * Updated endDate of a Deck to now
     *
     * @param deckId
     * @return Deck
     */
    @Override
    public Deck updateDeckEndDate(Integer deckId) {
        logger.debug("Called updateDeckEndDate in isDeckLimitNotReached for deck-{}", deckId);

        Deck deck = getDeckbyId(deckId);
        deck.setEndDate(System.currentTimeMillis());
        return deckRepository.save(deck);
    }
}
