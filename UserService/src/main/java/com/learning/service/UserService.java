package com.learning.service;

import com.learning.entities.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
