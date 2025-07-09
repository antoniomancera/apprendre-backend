package com.antonio.apprendrebackend.service.controller;


import com.antonio.apprendrebackend.service.exception.HomeNotFoundException;
import com.antonio.apprendrebackend.service.model.ErrorResponse;
import com.antonio.apprendrebackend.service.model.Home;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.HomeService;
import com.antonio.apprendrebackend.service.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    /**
     * Returns the information to be displayed in home
     *
     * @return Home respond with a Home object that includes userHistorial among other data
     * @throws HomeNotFoundException if not found any data related to home for an user
     */
    @GetMapping
    public @ResponseBody ResponseEntity<?> getHome() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Home home = homeService.getHome(userInfo);
        return ResponseEntity.ok(home);
    }
}
