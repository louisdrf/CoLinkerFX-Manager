package com.colinker.routing.localrouter.controllers;

import com.colinker.routing.localrouter.services.UserService;
import com.colinker.services.PasswordUtils;
import com.colinker.services.UserPropertiesService;
import com.colinker.views.ApiResponseModal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.colinker.models.User;

import java.util.Optional;

@Controller
public class LocalAuthRouter {

    private static UserService userService;

    @Autowired
    public LocalAuthRouter(UserService userService) { LocalAuthRouter.userService = userService; }

    public static boolean login(String username, String password) {
        Optional<User> user = userService.getUserByName(username);
        if(user.isEmpty()) {
            ApiResponseModal.showErrorModal("Identifiants incorrects.");
            return false;
        }
        Boolean areCredentialsCorrect = PasswordUtils.matches(password, user.get().getPassword());
        if(areCredentialsCorrect) {
            UserPropertiesService.saveToProperties("username", username);
        }
        else {
            ApiResponseModal.showErrorModal("Identifiants incorrects.");
        }
        return areCredentialsCorrect;
    }
}
