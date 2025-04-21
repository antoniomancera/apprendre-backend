package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.DeckUser;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeckUserRepository extends CrudRepository<DeckUser, Integer> {
    List<DeckUser> findByEndDateNullAndUserInfo(UserInfo userInfo);
}
