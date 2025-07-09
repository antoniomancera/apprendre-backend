package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.UserRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserRequestService {
    /**
     * Method for add a request from a user, if exist another request not answered in the last 7 days returns an error
     *
     * @param email
     * @param subject
     * @param message
     * @return
     */
    UserRequest addUserRequest(UserInfo userInfo, String email, String subject, String message);
}
