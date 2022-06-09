package com.bekzodkeldiyarov.websocketproject.controller;

import com.bekzodkeldiyarov.websocketproject.entity.Message;
import com.bekzodkeldiyarov.websocketproject.entity.User;
import com.bekzodkeldiyarov.websocketproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register/{username}")
    public ResponseEntity<Void> register(@PathVariable String username) {
        User userFromDb = userService.findByLogin(username);
        if (userFromDb == null) {
            userFromDb = new User(username);
            userService.save(userFromDb);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

}
