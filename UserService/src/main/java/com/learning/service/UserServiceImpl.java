package com.learning.service;

import com.learning.entities.User;
import com.learning.exceptions.UserServiceException;
import com.learning.repositories.UserRepository;
import com.learning.repositories.UserRepositoryImpl;

import java.util.UUID;


public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    private EmailVerificationService emailVerificationService;

    public UserServiceImpl(UserRepository userRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
        if(firstName.isEmpty())
            throw new IllegalArgumentException("User FirstName is Mandatory");
        if(lastName.isEmpty())
            throw new IllegalArgumentException("User lastName is Mandatory");
        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());
        boolean isUserCreated;
        try {
            isUserCreated = userRepository.save(user);
        }
        catch (RuntimeException e)
        {
            throw new UserServiceException(e.getMessage());
        }
        if(!isUserCreated)
            throw new UserServiceException("User not created");

        try {
            emailVerificationService.scheduleEmailVerification(user);
        }
        catch (RuntimeException e)
        {
            throw new UserServiceException(e.getMessage());
        }
        return user;

    }
}
