package com.antonio.apprendrebackend.controller;

import com.antonio.apprendrebackend.model.ErrorResponse;
import com.antonio.apprendrebackend.model.Palabra;
import com.antonio.apprendrebackend.repository.PalabraRepository;
import com.antonio.apprendrebackend.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/palabra")
public class PalabraController {
    @Autowired
    private PalabraRepository palabraRepository;

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<?> addNewPalabra(@RequestParam String name) {
        Optional<Palabra> existingPalabra = palabraRepository.findByName(name);
        if (existingPalabra.isPresent()) {
            ErrorResponse errorResponse = new ErrorResponse("La palabra ya existe", ErrorCode.PALABRA_EXISTS);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Palabra palabra = new Palabra(name);
        palabraRepository.save(palabra);

        return new ResponseEntity<>(palabra, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getByName")
    public @ResponseBody Optional<Palabra> getPalabraByName(@RequestParam String name) {
        return palabraRepository.findByName(name);
    }

    @GetMapping(path = "/getById")
    public @ResponseBody Optional<Palabra> getPalabraById(@RequestParam Integer palabraId) {
        return palabraRepository.findById(palabraId);
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<Palabra> getAllPalabra() {
        return palabraRepository.findAll();
    }
}