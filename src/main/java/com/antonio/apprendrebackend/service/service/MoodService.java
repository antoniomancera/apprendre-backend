package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Mood;

import java.util.List;

public interface MoodService {
    /**
     * Returns all the moods of a verb availables in the database
     *
     * @return List<Mood>
     */
    List<Mood> getAllMoods();
}
