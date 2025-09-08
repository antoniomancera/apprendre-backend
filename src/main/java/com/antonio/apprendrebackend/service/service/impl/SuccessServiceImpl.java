package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.HomeNotFoundException;
import com.antonio.apprendrebackend.service.exception.SuccessNotFoundException;
import com.antonio.apprendrebackend.service.model.Success;
import com.antonio.apprendrebackend.service.model.WordSense;
import com.antonio.apprendrebackend.service.repository.SuccessRepository;
import com.antonio.apprendrebackend.service.service.SuccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuccessServiceImpl implements SuccessService {
    private static final Logger logger = LoggerFactory.getLogger(SuccessServiceImpl.class);

    @Autowired
    private SuccessRepository successRepository;

    /**
     * Returns the success of a successEnum
     *
     * @param successEnum
     * @return Success
     * @throws SuccessNotFoundException if not found Succes for the successEnum
     */
    @Override
    public Success getSuccessBySuccessEnum(Success.SuccessEnum successEnum) {
        logger.debug("Getting the object Success for successEnum: %s", successEnum);

        return successRepository.findBySuccessEnum(successEnum).orElseThrow(() -> new SuccessNotFoundException(String.format("Not dounf any success for enum: %s", successEnum)));
    }

    /**
     * Return the success of an attempt given a wordSense
     *
     * @param attempt
     * @param wordSense
     * @return Success
     * @throws SuccessNotFoundException if not found Succes for the successEnum
     */
    @Override
    public Success getSuccessByAttemptAndWordSense(String attempt, WordSense wordSense) {
        logger.debug("Getting the object Success for attempt: %s, and wordSense: %d", attempt, wordSense.getId());

        String attemptLowerCase = attempt.toLowerCase();
        String wordLowerCase = wordSense.getWord().getName().toLowerCase();
        if (attemptLowerCase.equals(wordLowerCase))
            return getSuccessBySuccessEnum(Success.SuccessEnum.CORRECT);
        return getSuccessBySuccessEnum(Success.SuccessEnum.INCORRECT);
    }
}
