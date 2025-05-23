package com.antonio.apprendrebackend.service.model;

import com.antonio.apprendrebackend.service.dto.DeckUserDTO;
import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.dto.UserInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class Home {
    List<DailyStats> weekStats;
    GoalDTO goal;
    UserInfoDTO userInfo;
    List<DeckUserDTO> decks;
    Integer lastDeckId;
}
