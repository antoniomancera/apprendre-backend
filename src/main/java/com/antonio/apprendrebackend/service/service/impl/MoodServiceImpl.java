package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Mood;
import com.antonio.apprendrebackend.service.repository.MoodRepository;
import com.antonio.apprendrebackend.service.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodServiceImpl implements MoodService {
    @Autowired
    private MoodRepository moodRepository;

    /**
     * Returns all the moods of a verb availables in the database
     *
     * @return List<Mood>
     */
    @Override
    public List<Mood> getAllMoods() {
        return moodRepository.findAll();
    }
}
