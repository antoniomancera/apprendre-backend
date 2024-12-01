package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.UserRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserRequestService {
    UserRequest addUserRequest(@PathVariable String email, @RequestParam String subject, @RequestParam String message);
}
