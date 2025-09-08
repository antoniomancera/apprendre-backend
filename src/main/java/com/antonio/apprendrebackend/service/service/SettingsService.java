package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.SettingsOptionsDTO;

public interface SettingsService {
    /**
     * Get all the settings options availables
     *
     * @return SettingsOptionsDTO
     */
    SettingsOptionsDTO getSettingsOptions();
}
