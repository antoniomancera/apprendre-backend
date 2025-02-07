package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.DeckWordTranslationHistorialDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordTranslationHistorialNotFoundException;
import com.antonio.apprendrebackend.service.service.DeckWordTranslationHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/deckWordTranslationHistorial")
public class DeckWordTranslationHistorialController {
    @Autowired
    DeckWordTranslationHistorialService deckWordTranslationHistorialService;

    /**
     * Return all DeckWordTranslationHistorial for a day
     *
     * @param dayMillis
     * @return HTTP respond with a List<DeckWordTranslationHistorialDTO>
     * @throws DeckWordTranslationHistorialNotFoundException if not exist any DeckWordTranslationHistorial the dayMillis day
     */
    @GetMapping(path = "{dayMillis}")
    public @ResponseBody ResponseEntity<List<DeckWordTranslationHistorialDTO>> getDeckWordTranslationHistorialByDayMillis(@PathVariable Long dayMillis) {
        List<DeckWordTranslationHistorialDTO> deckWordTranslationHistorial = deckWordTranslationHistorialService.getDeckWordTranslationHistorialByDayMillis(dayMillis);
        return ResponseEntity.ok(deckWordTranslationHistorial);
    }
}
