package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.ErrorResponse;
import com.antonio.apprendrebackend.service.model.UserRequest;
import com.antonio.apprendrebackend.service.model.WordFr;
import com.antonio.apprendrebackend.service.repository.UserRequestRepository;
import com.antonio.apprendrebackend.service.service.UserRequestService;
import com.antonio.apprendrebackend.service.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        try {
            System.out.println("funciona");
            return new ResponseEntity<>(userRequestService.addUserRequest(email, subject, message), HttpStatus.CREATED);

        } catch (UserInfoNotFoundException e) {
            System.err.println("Excepción: " + e.getMessage());
            throw e;
        }
    }
}
