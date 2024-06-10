package com.colinker.routing.localrouter.controllers;

import com.colinker.routing.localrouter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LocalAuthRouter {

    private final UserService userService;

    @Autowired
    public LocalAuthRouter(UserService userService) { this.userService = userService; }
}
