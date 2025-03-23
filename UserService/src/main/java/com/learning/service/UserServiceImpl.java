package com.learning.service;

import com.learning.entities.User;

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        return new User();
    }
}
