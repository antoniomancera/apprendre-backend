package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.UserRequestExistLastWeekNotAnswered;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.UserRequest;
import com.antonio.apprendrebackend.service.repository.UserRequestRepository;
import com.antonio.apprendrebackend.service.service.UserInfoService;
import com.antonio.apprendrebackend.service.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static com.antonio.apprendrebackend.service.util.GeneralConstants.ONE_DAY_MILLIS;

@Service
public class UserRequestServiceImpl implements UserRequestService {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserRequestRepository userRequestRepository;

    @Override
    public UserRequest addUserRequest(@PathVariable String email, @RequestParam String subject, @RequestParam String message) {
        UserInfo userInfo = userInfoService.getByEmail(email);

        Optional<UserRequest> existingRequest = userRequestRepository
                .findFirstByUserInfoAndIsAnsweredFalseAndCreatedDateAfter(userInfo, System.currentTimeMillis() - (7 * ONE_DAY_MILLIS));

        if (existingRequest.isPresent()) {
            throw new UserRequestExistLastWeekNotAnswered("There is already an unanswered request for this user from the last week.");
        }
        UserRequest userRequest = new UserRequest(subject, message, userInfo);
        return userRequestRepository.save(userRequest);
    }
}
