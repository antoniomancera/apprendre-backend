package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.SuccessNotFoundException;
import com.antonio.apprendrebackend.service.model.Success;
import com.antonio.apprendrebackend.service.model.WordSense;

public interface SuccessService {
    /**
     * Returns the success of a successEnum
     *
     * @param successEnum
     * @return Success
     * @throws SuccessNotFoundException if not found Succes for the successEnum
     */
    Success getSuccessBySuccessEnum(Success.SuccessEnum successEnum);

    /**
     * Return the success of an attempt given a wordSense
     *
     * @param attempt
     * @param wordSense
     * @return Success
     * @throws com.antonio.apprendrebackend.service.exception.SuccessNotFoundException if not found Succes for the successEnum
     */
    Success getSuccessByAttemptAndWordSense(String attempt, WordSense wordSense);
}





