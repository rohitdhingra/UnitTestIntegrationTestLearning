package com.learning.service;

import com.learning.io.UsersDatabase;
import com.learning.io.UsersDatabaseMapImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId;

    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();

        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {

        //Arrange
        Map<String,String> user = new HashMap<>();
        user.put("firstName", "John");
        user.put("lastName", "Doe");


        //Act
        createdUserId = userService.createUser(user);

        //Assert
        assertNotNull(createdUserId,"User Id should not be null");


    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {

        //Arrange
        Map<String,String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName","John");
        newUserDetails.put("lastName","Dhingra");

        //Act

        Map updatedUserDetails = userService.updateUser(createdUserId, newUserDetails);

        //Assert
        assertEquals("Dhingra",newUserDetails.get("lastName"),"LastName is not updated");
        assertEquals("John",newUserDetails.get("firstName"),"First Name is not updated");

    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {

        //Act
        Map userDetails = userService.getUserDetails(createdUserId);

        //Assert
        assertEquals("Dhingra",userDetails.get("lastName"),"LastName is not updated");
        assertEquals("John",userDetails.get("firstName"),"First Name is not updated");


    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {

        //Act
        userService.deleteUser(createdUserId);

        assertNull(userService.getUserDetails(createdUserId),"User should not be found");
    }

}
