package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.SettingsOptionsDTO;
import com.antonio.apprendrebackend.service.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/settings")
public class SettingsController {
    private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private SettingsService settingsService;

    /**
     * Get all the settings options availables
     *
     * @return HTTP respond with SettingsOptionsDTO
     */
    @GetMapping
    public @ResponseBody ResponseEntity<SettingsOptionsDTO> getSettingsOptions(
    ) {
        logger.info("Called getSettingsOptions() in SettingsController");

        SecurityContextHolder.getContext().getAuthentication().getCredentials();
        SettingsOptionsDTO settingsOptions = settingsService.getSettingsOptions();
        return ResponseEntity.ok(settingsOptions);
    }
}
