package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Level;

import java.util.List;

public interface LevelService {
    /**
     * Returns all the levels available in the database
     *
     * @return List<Level>
     */
    List<Level> getAllLevels();
}
