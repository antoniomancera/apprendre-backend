package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/userRequest")
public class UserRequestController {

    @Autowired
    UserRequestService userRequestService;

    /**
     * Method for add a request from a user, or if the user  or exist another request in the last 7 days will return an error
     *
     * @param email
     * @param subject
     * @param message
     * @return
     */
    @PostMapping(path = "")
    public @ResponseBody ResponseEntity<?> addUserRequest(@RequestParam String email, @RequestParam String subject, @RequestParam String message) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        try {
            return new ResponseEntity<>(userRequestService.addUserRequest(userInfo, email, subject, message), HttpStatus.CREATED);

        } catch (UserInfoNotFoundException e) {
            System.err.println("Excepción: " + e.getMessage());
            throw e;
        }
    }
}
