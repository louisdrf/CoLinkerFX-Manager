package com.colinker.routing.localrouter.controllers;

import com.colinker.models.User;
import com.colinker.routing.localrouter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class LocalUserRouter {

    private static UserService userService;

    @Autowired
    public LocalUserRouter(UserService userService) {
        LocalUserRouter.userService = userService;
    }

    public static void createUser(User user) {
        userService.createUser(user.getUsername(), user.getPassword());
    }

    public Optional<User> getUserByName(String username) { return userService.getUserByName(username); }
}
