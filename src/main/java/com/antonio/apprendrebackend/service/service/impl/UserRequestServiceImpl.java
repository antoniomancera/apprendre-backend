package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.UserRequestExistLastWeekNotAnswered;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.UserRequest;
import com.antonio.apprendrebackend.service.repository.UserRequestRepository;
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
    UserRequestRepository userRequestRepository;

    /**
     * Method for add a request from a user, if exist another request not answered in the last 7 days returns an error
     *
     * @param email
     * @param subject
     * @param message
     * @return
     */
    @Override
    public UserRequest addUserRequest(UserInfo userInfo, String email, String subject, String message) {
        Optional<UserRequest> existingRequest = userRequestRepository
                .findFirstByUserInfoAndIsAnsweredFalseAndCreatedDateAfter(userInfo, System.currentTimeMillis() - (7 * ONE_DAY_MILLIS));

        if (existingRequest.isPresent()) {
            throw new UserRequestExistLastWeekNotAnswered("There is already an unanswered request for this user from the last week.");
        }
        UserRequest userRequest = new UserRequest(subject, message, userInfo);
        return userRequestRepository.save(userRequest);
    }
}
