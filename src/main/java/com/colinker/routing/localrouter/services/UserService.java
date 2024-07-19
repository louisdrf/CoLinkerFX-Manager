package com.colinker.routing.localrouter.services;

import com.colinker.models.User;
import com.colinker.routing.localrouter.controllers.LocalUserRouter;
import com.colinker.routing.localrouter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);
    }

    public Optional<User> getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public String getUserIdByName(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getId).orElse(null);
    }
}
