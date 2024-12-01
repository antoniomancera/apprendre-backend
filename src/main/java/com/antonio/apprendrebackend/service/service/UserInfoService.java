package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;

public interface UserInfoService {
    UserInfo getUserInfo();

    UserInfo getByEmail(String email) throws UserInfoNotFoundException;
}
