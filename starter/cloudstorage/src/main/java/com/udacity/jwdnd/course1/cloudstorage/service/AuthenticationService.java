package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private final UserService userService;
    private final HashService hashService;

    public AuthenticationService(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Provided credentials on login attempt
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Try get user from database, and compare entered password with the hashed password
        // stored on the database
        User user = userService.getByUsername(username);
        if (user != null) {
            String hashedPassword = hashService.getHashedValue(password, user.getSalt());
            if(hashedPassword.equals(user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
