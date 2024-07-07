package com.antonio.apprendrebackend.controller;

import com.antonio.apprendrebackend.model.ErrorResponse;
import com.antonio.apprendrebackend.model.WordSp;
import com.antonio.apprendrebackend.repository.WordSpRepository;
import com.antonio.apprendrebackend.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/wordSp")
public class WordSpController {
    @Autowired
    private WordSpRepository wordSpRepository;

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<?> addNewWordEs(@RequestParam String name) {
        Optional<WordSp> existingWordEs = wordSpRepository.findByName(name);
        if (existingWordEs.isPresent()) {
            ErrorResponse errorResponse = new ErrorResponse("La wordSp ya existe", ErrorCode.WORD_ES_EXISTS);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        WordSp wordSp = new WordSp(name);
        wordSpRepository.save(wordSp);

        return new ResponseEntity<>(wordSp, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getByName")
    public @ResponseBody Optional<WordSp> getWordEsByName(@RequestParam String name) {
        return wordSpRepository.findByName(name);
    }

    @GetMapping(path = "/getById")
    public @ResponseBody Optional<WordSp> getWordEsById(@RequestParam Integer wordEsId) {
        return wordSpRepository.findById(wordEsId);
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<WordSp> getAllWordEs() {
        return wordSpRepository.findAll();
    }
}