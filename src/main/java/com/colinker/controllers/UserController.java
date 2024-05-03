package com.colinker.controllers;

import com.colinker.models.User;
import com.colinker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // Assurez-vous que cette annotation est présente
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser(User user) {
        userService.createUser(user.getUsername(), user.getPassword());
    }
}
