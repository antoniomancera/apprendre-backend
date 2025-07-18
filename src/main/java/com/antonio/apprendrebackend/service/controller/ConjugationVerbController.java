package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.ConjugationTenseDTO;
import com.antonio.apprendrebackend.service.service.ConjugationVerbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/conjugationVerb")
public class ConjugationVerbController {
    @Autowired
    private ConjugationVerbService conjugationVerbService;

    /**
     * Given a wordSense return a list with the conjugationComplete(regular and irregular) with of all the tenses
     *
     * @param wordSenseId
     * @return HTTP respond with List<ConjugationTenseDTO>
     * @throws ConjugationVariationFoundException if not exist a ConjugationVariation fo the verb
     */
    @GetMapping(path = "/allComplete/wordSense/{wordSenseId}")
    public @ResponseBody ResponseEntity<List<ConjugationTenseDTO>> getAllConjugationCompleteByWordSenseId(@PathVariable Integer wordSenseId) {
        List<ConjugationTenseDTO> conjugationTenseDTOS = conjugationVerbService.getConjugationComplete(wordSenseId);
        return ResponseEntity.ok(conjugationTenseDTOS);
    }

    /**
     * Given a word return a list with the conjugationComplete(regular and irregular) with of all the tenses
     *
     * @param wordId
     * @return HTTP respond with List<ConjugationTenseDTO>
     * @throws ConjugationVariationFoundException if not exist a ConjugationVariation fo the verb
     */
    @GetMapping(path = "/allComplete/word/{wordId}")
    public @ResponseBody ResponseEntity<List<ConjugationTenseDTO>> getAllConjugationCompleteByWordId(@PathVariable Integer wordId) {
        List<ConjugationTenseDTO> conjugationTenseDTOS = conjugationVerbService.getConjugationComplete(wordId);
        return ResponseEntity.ok(conjugationTenseDTOS);
    }
}
