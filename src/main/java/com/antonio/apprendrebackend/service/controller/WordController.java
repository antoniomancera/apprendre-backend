package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.dto.WordWithSenseDTO;
import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/word")
public class WordController {
    private static final Logger logger = LoggerFactory.getLogger(WordController.class);
    @Autowired
    WordService wordService;

    /**
     * Get all the words that are verbs
     *
     * @return HTTP respond with a List<WordDTO>
     * @throws TypeNotFoundException if not exist Verb as a type
     * @throws WordNotFoundException if not exist any Verb
     */
    @GetMapping(path = "/allVerbs")
    public @ResponseBody ResponseEntity<List<WordDTO>> getAllVerbs() {
        logger.info("Get a list with all the verbs");

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordDTO> verbs = wordService.getAllVerbs();
        return ResponseEntity.ok(verbs);
    }


    /**
     * Return a page with wordWitSenses, that is a collection of word with their respective wordSense
     *
     * @param pageNumber
     * @param pageSize
     * @return HTTP respond with a List<WordWithSenseDTO>
     */
    @GetMapping(path = "/paginated/{pageNumber}/{pageSize}")
    public @ResponseBody ResponseEntity<List<WordWithSenseDTO>> getWordWithSensePaginated(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        logger.info(String.format("Get the page: %d with: %d elements of words with theirs wordSenses", pageNumber, pageSize));

        List<WordWithSenseDTO> words = wordService.getWordWithSensePaginated(pageNumber, pageSize);
        return ResponseEntity.ok(words);
    }
}
