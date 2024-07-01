package com.antonio.apprendrebackend.controller;

import com.antonio.apprendrebackend.dto.MotPalabraDTO;
import com.antonio.apprendrebackend.model.ErrorResponse;
import com.antonio.apprendrebackend.model.Mot;
import com.antonio.apprendrebackend.repository.MotRepository;
import com.antonio.apprendrebackend.service.MotPalabraService;
import com.antonio.apprendrebackend.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/motPalabra")
public class MotPalabraController {
    @Autowired
    private MotPalabraService motPalabraService;


    @GetMapping(path = "/getRandom")
    public @ResponseBody ResponseEntity<?> getRandomMotPalabraPhrase() {
        MotPalabraDTO motPalabraDTO = motPalabraService.getRandomMotPalabraPhrase();
        if (motPalabraDTO == null) {
            ErrorResponse errorResponse = new ErrorResponse("No se ha encontrado MotPalabra", ErrorCode.MOT_PALABRA_NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(motPalabraDTO, HttpStatus.CREATED);
    }


}