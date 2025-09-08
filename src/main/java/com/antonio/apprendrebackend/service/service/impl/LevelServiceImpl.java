package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Level;
import com.antonio.apprendrebackend.service.repository.LevelRepository;
import com.antonio.apprendrebackend.service.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {
    @Autowired
    private LevelRepository levelRepository;

    /**
     * Returns all the levels available in the database
     *
     * @return List<Level>
     */
    @Override
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }
}
