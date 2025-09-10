package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.DeckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
}