package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.UserHistorialDTO;
import com.antonio.apprendrebackend.service.exception.UserHistorialNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.UserHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/userHistorial")
public class UserHistorialController {
    @Autowired
    UserHistorialService userHistorialService;

    /**
     * Return all DeckWordTranslationHistorial for a day
     *
     * @param dayMillis
     * @return HTTP respond with a List<DeckWordTranslationHistorialDTO>
     * @throws UserHistorialNotFoundException if not exist any DeckWordTranslationHistorial the dayMillis day
     */
    @GetMapping(path = "{dayMillis}")
    public @ResponseBody ResponseEntity<List<UserHistorialDTO>> getDeckWordTranslationHistorialByDayMillis(@PathVariable Long dayMillis) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<UserHistorialDTO> deckWordTranslationHistorial = userHistorialService.getDeckWordTranslationHistorialByDayMillis(userInfo, dayMillis);
        return ResponseEntity.ok(deckWordTranslationHistorial);
    }
}
