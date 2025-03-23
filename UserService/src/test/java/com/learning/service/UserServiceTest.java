package com.learning.service;

import com.learning.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserServiceTest {
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject(){
        //Arrange
        String firstName = "Rohit";
        String lastName = "Sharma";
        String email = "rsharma@gmail.com";
        String password = "12345678";
        String repeatPassword = "12345678";
        UserService userService = new UserServiceImpl();


        //Act
        User user = userService.createUser( firstName, lastName, email, password, repeatPassword);

        //Assert

        assertNotNull(user,"create method should not be null");
    }
}
