package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.UserRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRequestRepository extends CrudRepository<UserRequest, Integer> {
    Optional<UserRequest> findFirstByUserInfoAndIsAnsweredFalseAndCreatedDateAfter(UserInfo userInfo, Long createdDate);
}
