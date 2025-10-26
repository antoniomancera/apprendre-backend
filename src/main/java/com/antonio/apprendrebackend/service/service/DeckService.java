package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.CreationOptionsAvailableDTO;
import com.antonio.apprendrebackend.service.exception.DeckAlreadyExistsException;
import com.antonio.apprendrebackend.service.exception.DeckNotFoundException;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;

import java.util.List;

public interface DeckService {
    /**
     * Get all active decks by user
     *
     * @return List<Deck>
     */
    List<Deck> getActiveDecks(UserInfo userInfo);

    /**
     * Get a deck by their id
     *
     * @param deckId
     * @return Deck
     * @throws DeckNotFoundException if not found anyone with their id
     */
    Deck getDeckbyId(Integer deckId);

    /**
     * Create a new Deck
     *
     * @param deck
     * @return Deck
     * @throws DeckAlreadyExistsException if exists a Deck with the same name
     */
    Deck createDeck(Deck deck);

    /**
     * Get if the user has reached the  of decks already in use
     *
     * @return Boolean
     */
    Boolean isDeckLimitNotReached(Integer userId);

    /**
     * Updated endDate of a Deck to now
     *
     * @param deckId
     * @return Deck
     */
    Deck updateDeckEndDate(Integer deckId);

    /**
     * Return true if exist a deck already removed by the user
     *
     * @param userId
     * @return boolean
     */
    boolean existsDeckByEndDateNotNullAndUserInfo(Integer userId);

    /**
     * Return if is possible to create a new Deck o to recycle one already removed
     *
     * @param userId
     * @return CreationOptionsAvailableDTO
     */
    CreationOptionsAvailableDTO isDeckCreationOptionsAvailable(Integer userId);
}
