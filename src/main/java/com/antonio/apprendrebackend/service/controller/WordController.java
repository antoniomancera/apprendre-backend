package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/word")
public class WordController {
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
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        System.out.println(userInfo.getId());
        List<WordDTO> verbs = wordService.getAllVerbs();
        return ResponseEntity.ok(verbs);
    }
}
