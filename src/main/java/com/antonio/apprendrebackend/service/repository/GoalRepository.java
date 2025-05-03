package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal, Integer> {
    Goal findFirstByUserInfoOrderByBeginDateDesc(UserInfo userInfo);
}
