package com.antonio.apprendrebackend.controller;

import com.antonio.apprendrebackend.model.Mot;
import com.antonio.apprendrebackend.model.Type;
import com.antonio.apprendrebackend.repository.MotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/mot")
public class MotController {
    @Autowired
    private MotRepository motRepository;

    @PostMapping(path = "/add")
    public @ResponseBody Mot addNewMot(@RequestParam String name
            , @RequestParam Type type) {
        Mot mot = new Mot(name, type);

        return motRepository.save(mot);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Mot> getAllMots() {
        return motRepository.findAll();
    }
}