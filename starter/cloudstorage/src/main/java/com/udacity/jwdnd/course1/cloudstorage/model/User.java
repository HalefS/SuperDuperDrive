package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {

    private Integer userId;
    private String username;
    private String firstName;
    private String lastName;
    private String salt;
    private String password;

    public User() {
        // for object bindings
    }

    public User(String username, String firstName, String lastName, String salt, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salt = salt;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSalt() {
        return salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
