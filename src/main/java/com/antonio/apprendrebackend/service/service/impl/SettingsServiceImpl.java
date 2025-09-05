package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.SettingsOptionsDTO;
import com.antonio.apprendrebackend.service.service.CourseService;
import com.antonio.apprendrebackend.service.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {
    private static final Logger logger = LoggerFactory.getLogger(SettingsServiceImpl.class);

    @Autowired
    private CourseService courseService;

    /**
     * Get all the settings options availables
     *
     * @return SettingsOptionsDTO
     */
    @Override
    public SettingsOptionsDTO getSettingsOptions() {
        logger.debug("Called getSettingsOptions() in SettingsService");
        
        SettingsOptionsDTO settings = new SettingsOptionsDTO();
        settings.setCourses(courseService.getAllCourses());

        return settings;
    }
}
