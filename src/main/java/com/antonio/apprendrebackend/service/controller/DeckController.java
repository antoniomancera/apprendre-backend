package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.CreationOptionsAvailableDTO;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.DeckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/deck")
public class DeckController {
    private static final Logger logger = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    private DeckService deckService;


    /**
     * Get if the user has reached the  of decks already in use
     *
     * @return HTTP respond with a boolean
     */
    @GetMapping(path = "/isDeckLimitNotReached")
    public @ResponseBody ResponseEntity<Boolean> isDeckLimitNotReached() {
        logger.info("Called isDeckLimitNotReached() in DeckController");

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Boolean isDeckLimitNotReached = deckService.isDeckLimitNotReached(userInfo.getId());
        return ResponseEntity.ok(isDeckLimitNotReached);
    }

    /**
     * After clicking in remove a deck, the endDate is updated
     *
     * @param deckId
     * @return HTTP respond with the updated Deck
     */
    @PatchMapping("{deckId}")
    public ResponseEntity<Deck> updateDeckEndDate(@PathVariable Integer deckId) {
        logger.info("Called updateDeckEndDate() in DeckController for deck-{}", deckId);

        SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Deck updateDeckEndDate = deckService.updateDeckEndDate(deckId);
        return ResponseEntity.ok(updateDeckEndDate);
    }

    /**
     * Get all active decks by user
     *
     * @return HTTP respond with the active decks
     */
    @GetMapping()
    public @ResponseBody ResponseEntity<List<Deck>> getActiveDecks() {
        logger.info("Called getActiveDecks() in DeckController");

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<Deck> decks = deckService.getActiveDecks(userInfo);
        return ResponseEntity.ok(decks);
    }


    /**
     * Return if is possible to create the different types of decks
     *
     * @return HTTP respond with a CreationOptionsAvailableDTO
     */
    @GetMapping(path = "/isDeckCreationOptionsAvailable")
    public @ResponseBody ResponseEntity<CreationOptionsAvailableDTO> isDeckCreationOptionsAvailable() {
        logger.info("Called isDeckCreationOptionsAvailable() in DeckController");

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        CreationOptionsAvailableDTO isDeckLimitNotReached = deckService.isDeckCreationOptionsAvailable(userInfo.getId());
        return ResponseEntity.ok(isDeckLimitNotReached);
    }

}