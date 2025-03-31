package com.learning.ui.controllers;

import com.learning.ui.controllers.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//        , properties = {"server.port=8081","hostname=192.168.0.2"}
)
//@TestPropertySource(locations = "/application-test.properties"
//, properties = {"server.port=8081"})
//@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersControllerIntegrationTest{

    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authorizationToken;

    @Test
    @Order(1)
    void testCreateUser_whenValidDetailsProvide_returnsUserDetails() throws JSONException {
//        String createUserJson = "{\n" +
//                "    \"firstName\":\"Rohit\",\n" +
//                "    \"lastName\":\"Dhingra\",\n" +
//                "    \"email\":\"rdhingra86@gmail.com\",\n" +
//                "    \"password\":\"12345678\",\n" +
//                "    \"repeatPassword\":\"12345678\"\n" +
//                "}";

        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName","Rohit");
        userDetailsRequestJson.put("lastName","Dhingra");
        userDetailsRequestJson.put("email","rdhingra86@gmail.com");
        userDetailsRequestJson.put("password","12345678");
        userDetailsRequestJson.put("repeatPassword","12345678");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), httpHeaders);

        //Act
        ResponseEntity<UserRest> userRestResponseEntity = restTemplate.postForEntity("/users", request, UserRest.class);
        UserRest userRest = userRestResponseEntity.getBody();


        //Assert
        Assertions.assertEquals(HttpStatus.OK, userRestResponseEntity.getStatusCode());
        Assertions.assertEquals(userDetailsRequestJson.getString("firstName"), userRest.getFirstName());
    }

    @Test
    @Order(2)
    void testGetUsers_whenMissingJWT_returns403()
    {
        //Arrange
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", "application/json");

        HttpEntity request = new HttpEntity<>(null,httpHeaders);

        //Act
        ResponseEntity<List<UserRest>> responseEntity = restTemplate.exchange("/users", HttpMethod.GET, request, new ParameterizedTypeReference<List<UserRest>>() {
        });
        //Assert
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    @Order(3)
    void testUserLogin_whenValidCredentialsProvided_returnsJwtInAuthorizationHeader() throws JSONException {
        //Arrange
        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email","rdhingra86@gmail.com");
        loginCredentials.put("password","12345678");

        HttpEntity<String> requestEntity = new HttpEntity<>(loginCredentials.toString());

        //Act
        ResponseEntity responseEntity = restTemplate.postForEntity("/users/login", requestEntity, null);
        authorizationToken = responseEntity.getHeaders().get("Authorization").get(0);
        //assert
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getHeaders().getValuesAsList(HttpHeaders.AUTHORIZATION).get(0));
        Assertions.assertNotNull(responseEntity.getHeaders().getValuesAsList("UserID").get(0));
    }

    @Test
    @Order(4)
    void testGetUsers_whenValidJwtProvided_returnsUsers()
    {
        //Arrange
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", "application/json");
        httpHeaders.setBearerAuth(authorizationToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);


        //Act

        ResponseEntity<List<UserRest>> listResponseEntity = restTemplate.exchange("/users", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserRest>>() {
        });


        //Assert

        Assertions.assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        Assertions.assertTrue(listResponseEntity.getBody().size() == 1);
    }

}
