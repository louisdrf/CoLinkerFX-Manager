package com.colinker.routing.localrouter.controllers;

import com.colinker.models.User;
import com.colinker.routing.localrouter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LocalUserRouter {

    private final UserService userService;

    @Autowired
    public LocalUserRouter(UserService userService) {
        this.userService = userService;
    }

    public void createUser(User user) {
        userService.createUser(user.getUsername(), user.getPassword());
    }
}
