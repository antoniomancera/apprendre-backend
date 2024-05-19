package com.antonio.apprendrebackend.controller;

import com.antonio.apprendrebackend.model.ErrorResponse;
import com.antonio.apprendrebackend.model.Mot;
import com.antonio.apprendrebackend.model.Palabra;
import com.antonio.apprendrebackend.repository.MotRepository;
import com.antonio.apprendrebackend.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/mot")
public class MotController {
    @Autowired
    private MotRepository motRepository;

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<?> addNewMot(@RequestParam String name) {
        Optional<Mot> existingMot = motRepository.findByName(name);
        if (existingMot.isPresent()) {
            ErrorResponse errorResponse = new ErrorResponse("El Mot ya existe", ErrorCode.MOT_EXISTS);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Mot mot = new Mot(name);
        motRepository.save(mot);

        return new ResponseEntity<>(mot, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getByName")
    public @ResponseBody Optional<Mot> getMotByName(@RequestParam String name) {
        return motRepository.findByName(name);
    }

    @GetMapping(path = "/getById")
    public @ResponseBody Optional<Mot> getMotById(@RequestParam Integer motId) {
        return motRepository.findById(motId);
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<Mot> getAllMot() {
        return motRepository.findAll();
    }
}