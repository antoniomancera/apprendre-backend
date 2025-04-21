package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.UserGoal;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<UserGoal, Integer> {
    UserGoal findFirstByUserInfoOrderByBeginDateDesc(UserInfo userInfo);
}
