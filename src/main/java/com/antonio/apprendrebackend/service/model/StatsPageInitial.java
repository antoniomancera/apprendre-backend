package com.antonio.apprendrebackend.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class StatsPageInitial {
    List<DailyStats> weekStats;
}
