package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Category;
import com.antonio.apprendrebackend.service.repository.WordSenseCategoryRepository;
import com.antonio.apprendrebackend.service.service.WordSenseCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordSenseCategoryServiceImpl implements WordSenseCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(WordSenseCategoryServiceImpl.class);

    @Autowired
    private WordSenseCategoryRepository wordSenseCategoryRepository;

    /**
     * Get all the categories of a wordSense
     *
     * @param wordSenseId
     * @return List<Category>
     */
    @Override
    public List<Category> getCategoryByWordSenseId(Integer wordSenseId) {
        logger.debug("Called getCategoryByWordSenseId() in WordSenseCategory for wordSense-{}", wordSenseId);

        return wordSenseCategoryRepository.findByWordSenseId(wordSenseId).stream().map(wordSenseCategory -> wordSenseCategory.getCategory()).collect(Collectors.toList());
    }
}
