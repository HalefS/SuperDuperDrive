package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public int create(User user) {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.createUser(new User(user.getUsername(), user.getFirstName(), user.getLastName(), encodedSalt, hashedPassword));
    }

    public User getById(int id) {
        return userMapper.getById(id);
    }

    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    public int getIdFromUsername(String username) {
        User user = userMapper.getByUsername(username);
        return user.getUserId();
    }

    public void delete(int id) {
        userMapper.deleteById(id);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getByUsername(username) == null;
    }
}
