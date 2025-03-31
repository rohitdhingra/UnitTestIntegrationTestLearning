package com.learning.ui.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.service.UsersService;
import com.learning.service.UsersServiceImpl;
import com.learning.shared.UserDto;
import com.learning.ui.controllers.request.UserDetailsRequestModel;
import com.learning.ui.controllers.response.UserRest;
import org.h2.engine.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
//@AutoConfigureMockMvc(addFilters = false)
//@MockBean({ UsersServiceImpl.class })
public class UsersControllerWebLayerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
//    @Autowired
    UsersService usersService;

    private UserDetailsRequestModel userDetailsRequestModel;
    @BeforeEach
    void setUp() {
        userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("John");
        userDetailsRequestModel.setLastName("Doe");
        userDetailsRequestModel.setEmail("john@doe.com");
        userDetailsRequestModel.setPassword("password");
        userDetailsRequestModel.setRepeatPassword("password");

    }

    @Test
    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception {

        //Arrange

//        userDetailsRequestModel.setFirstName("");
//        UserDto userDto = new UserDto();
//        userDto.setFirstName("John");
//        userDto.setLastName("Doe");
//        userDto.setEmail("john@doe.com");
//        userDto.setUserId(UUID.randomUUID().toString());

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());

        Mockito.when(usersService.createUser(Mockito.any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();


        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserRest createdUser = new ObjectMapper().readValue(responseBodyAsString, UserRest.class);
        //Assert
        Assertions.assertEquals(userDetailsRequestModel.getFirstName(), createdUser.getFirstName());
        Assertions.assertEquals(userDetailsRequestModel.getLastName()  , createdUser.getLastName());
        Assertions.assertEquals(userDetailsRequestModel.getEmail(), createdUser.getEmail());
        Assertions.assertFalse(createdUser.getUserId().isEmpty());
    }


    @Test
    void testCreateUser_whenFirstNameIsLessThan2Characters_throwsBadRequest() throws Exception {

        //Arrange
        userDetailsRequestModel.setFirstName("j");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

    }

    @Test
    void testCreateUser_whenFirstNameNotProvide_throwsBadRequest() throws Exception {

        //Arrange
        userDetailsRequestModel.setFirstName("");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

    }
}


