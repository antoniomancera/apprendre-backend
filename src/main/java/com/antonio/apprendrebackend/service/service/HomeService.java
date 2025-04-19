package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.HomeNotFoundException;
import com.antonio.apprendrebackend.service.model.Home;
import com.antonio.apprendrebackend.service.model.UserInfo;

public interface HomeService {
    /**
     * Returns the information to be displayed in home
     *
     * @return Home respond with a Home object that includes userHistorial among other data
     * @throws HomeNotFoundException if not found any data related to home for an user
     */
    Home getHome(UserInfo userInfo);
}
