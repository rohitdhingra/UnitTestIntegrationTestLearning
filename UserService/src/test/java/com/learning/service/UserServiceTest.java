package com.learning.service;

import com.learning.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;
    UserService userService;

    @BeforeEach
    void init()
    {
        firstName = "Rohit";
        lastName = "Sharma";
        email = "rsharma@gmail.com";
        password = "12345678";
        repeatPassword = "12345678";
        userService = new UserServiceImpl();
    }
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject(){
        //Arrange



        //Act
        User user = userService.createUser( firstName, lastName, email, password, repeatPassword);

        //Assert

        assertNotNull(user,"create method should not be null");
        assertEquals(firstName,user.getFirstName(),"First name should be same as provided");
        assertEquals(lastName,user.getLastName(),"Last name should be same as provided");
        assertEquals(email,user.getEmail(),"Email should be same as provided");
        assertNotNull(user.getId());
    }

    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException()
    {
        //Arrange
       firstName = null ;



        //Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userService.createUser(firstName, lastName, email, password, repeatPassword));

        //Assert
        assertEquals("User FirstName is Mandatory",thrown.getMessage());

    }

}
