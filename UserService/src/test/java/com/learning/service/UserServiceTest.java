package com.learning.service;

import com.learning.entities.User;

import com.learning.exceptions.EmailNotificationServiceException;
import com.learning.exceptions.UserServiceException;
import com.learning.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doCallRealMethod;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;
    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void init()
    {
        firstName = "Rohit";
        lastName = "Sharma";
        email = "rsharma@gmail.com";
        password = "12345678";
        repeatPassword = "12345678";
    }
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject(){
        //Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        //Act
        User user = userService.createUser( firstName, lastName, email, password, repeatPassword);

        //Assert

        assertNotNull(user,"create method should not be null");

        assertEquals(firstName,user.getFirstName(),"First name should be same as provided");
        assertEquals(lastName,user.getLastName(),"Last name should be same as provided");
        assertEquals(email,user.getEmail(),"Email should be same as provided");
        assertNotNull(user.getId());
        Mockito.verify(userRepository,Mockito.times(1)).save(Mockito.any(User.class));
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

    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException()
    {
        //Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        //Act & Assert
        UserServiceException thrown = assertThrows(UserServiceException.class, () -> userService.createUser(firstName, lastName, email, password, repeatPassword),"Should throw UserServiceException");

        //Assert
//        assertEquals("User not created",thrown.getMessage());
    }

    @Test
    @DisplayName("Email Notification Exception is handled")
    void testCreateUser_whenEmailVerificationServiceThrowsException_thenThrowsUserServiceException() {
        //Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);
        Mockito.doThrow(EmailNotificationServiceException.class)
                .when(emailVerificationService)
                .scheduleEmailVerification(Mockito.any(User.class));
//        Mockito.doNothing().when(emailVerificationService).scheduleEmailVerification(Mockito.any(User.class));
        //Act & Assert
        UserServiceException thrown = assertThrows(UserServiceException.class, () -> userService.createUser(firstName, lastName, email, password, repeatPassword), "Should throw UserServiceException");

        //Assert
        Mockito.verify(emailVerificationService, Mockito.times(1)).scheduleEmailVerification(Mockito.any(User.class));
    }

    @Test
    void testCreateUser_whenUserCreated_schedulesEmailConfirmation() {
        //Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);
        Mockito.doCallRealMethod().when(emailVerificationService).scheduleEmailVerification(Mockito.any(User.class));

        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        //Assert
        Mockito.verify(emailVerificationService, Mockito.times(1)).scheduleEmailVerification(user);
    }
}
