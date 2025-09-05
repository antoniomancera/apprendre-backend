package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/userInfo")
public class UserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    //TODO eliminar

    /**
     * Get the UserInfo given the SupabaseId
     *
     * @param supabaseId
     * @return UserInfo
     * @throws UserInfoNotFoundException if not exist any UserInfo
     */
    @GetMapping(path = "{supabaseId}")
    public @ResponseBody ResponseEntity<UserInfo> getUserInfoBySupabaseId(
            @PathVariable String supabaseId

    ) {
        UserInfo userInfo = userInfoService.findBySupabaseId(supabaseId);
        return ResponseEntity.ok(userInfo);
    }

    @PutMapping(path = "setCurrentCourse/{courseCode}")
    public @ResponseBody ResponseEntity<UserInfo> setCurrentCourse(
            @PathVariable String courseCode
    ) {
        logger.info("Called setCurrentCourse() in UserInfoController");

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return ResponseEntity.ok(userInfoService.setUserInfoCurrentCourse(courseCode, userInfo));
    }
}
