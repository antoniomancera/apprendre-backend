package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeckEditInitInfoDTO {
    private Map<Integer, List<Integer>> wordToWordSensesIdMap;
    private List<WordWithAttemptsAndSuccessDTO> wordWithAttemptsAndSuccesses;
}
