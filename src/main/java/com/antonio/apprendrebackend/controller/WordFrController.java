package com.antonio.apprendrebackend.controller;

import com.antonio.apprendrebackend.model.ErrorResponse;
import com.antonio.apprendrebackend.model.WordFr;
import com.antonio.apprendrebackend.repository.WordFrRepository;
import com.antonio.apprendrebackend.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/wordFr")
public class WordFrController {
    @Autowired
    private WordFrRepository wordFrRepository;

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<?> addNewWordFr(@RequestParam String name) {
        Optional<WordFr> existingWordFr = wordFrRepository.findByName(name);
        if (existingWordFr.isPresent()) {
            ErrorResponse errorResponse = new ErrorResponse("La wordFr ya existe", ErrorCode.WORD_FR_EXISTS);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        WordFr wordFr = new WordFr(name);
        wordFrRepository.save(wordFr);

        return new ResponseEntity<>(wordFr, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getByName")
    public @ResponseBody Optional<WordFr> getWordFrByName(@RequestParam String name) {
        return wordFrRepository.findByName(name);
    }

    @GetMapping(path = "/getById")
    public @ResponseBody Optional<WordFr> getWordFrById(@RequestParam Integer wordFrId) {
        return wordFrRepository.findById(wordFrId);
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<WordFr> getAllWordFr() {
        return wordFrRepository.findAll();
    }
}