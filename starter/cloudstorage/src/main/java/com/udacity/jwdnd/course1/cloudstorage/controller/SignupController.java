package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.HashService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;


    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(Model model) {
        model.addAttribute("userForm", new User());
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute("userForm") User user, Model model) {
        // Error message to be displayed on template
        String signupError = null;

        // username availability
        if(!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "Username is not available";
            model.addAttribute("signupError", signupError);
            return "signup";
        }

        // Insert user into db and check for errors
        int rowsAdded = userService.create(user);
        if (rowsAdded < 1) {
            signupError = "Error signing you up, please try again";
            model.addAttribute("signupError", signupError);
            return "signup";
        }

        // everything went ok
        model.addAttribute("signupSuccess", true);
        return "signup";

    }

}
