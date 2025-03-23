package com.learning.service;

import com.learning.entities.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        if(firstName.isEmpty())
            throw new IllegalArgumentException("User FirstName is Mandatory");
        return new User(firstName,lastName,email, UUID.randomUUID().toString());
    }
}
