package com.antonio.apprendrebackend.service.controller;


import com.antonio.apprendrebackend.service.model.ErrorResponse;
import com.antonio.apprendrebackend.service.model.Home;
import com.antonio.apprendrebackend.service.service.HomeService;
import com.antonio.apprendrebackend.service.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    /**
     * Returns the information to be displayed in home
     *
     * @return Home
     */
    @GetMapping
    public @ResponseBody ResponseEntity<?> getHome() {
        Home home = homeService.getHome();
        if (home == null) {
            ErrorResponse errorResponse = new ErrorResponse("No se ha encontrado Home", ErrorCode.HOME_NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(home, HttpStatus.CREATED);
    }
}
