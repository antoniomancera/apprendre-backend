package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

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
}
