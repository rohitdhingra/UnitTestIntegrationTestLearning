package com.learning.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;
    private UserEntity userEntity;


    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("john@doe.com");
        userEntity.setEncryptedPassword("12345678");
    }

    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnStoredUserDetails() {

        //Act
        UserEntity storedUserEntity = entityManager.persistAndFlush(userEntity);

        //Assert
        Assertions.assertTrue(userEntity.getId() > 0);
        Assertions.assertEquals(userEntity.getFirstName(), storedUserEntity.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), storedUserEntity.getLastName());
        Assertions.assertEquals(userEntity.getEmail(), storedUserEntity.getEmail());
        Assertions.assertEquals(userEntity.getEncryptedPassword(), storedUserEntity.getEncryptedPassword());

    }

    @Test
    void testUserEntity_whenFirstNameIsTooLong_shouldThrowException() {
        //Arrange
        userEntity.setFirstName("asdsadasdasdasdasdasdasdasdasdasdasdsadsadadaddsadasdasdadadasdsadasdasdasdasdasdasdasdasdasdasdasdasdasd");


        //Act & Assert
        Assertions.assertThrows(PersistenceException.class, () -> {entityManager.persistAndFlush(userEntity);});
    }

    @Test
    void testUserEntity_whenExistingUserIdIsProvided_shouldThrowException() {

        userEntity.setUserId("1");

        entityManager.persistAndFlush(userEntity);
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUserId("1");
        newUserEntity.setFirstName("John");
        newUserEntity.setLastName("Doe");
        newUserEntity.setEmail("john@doe.com");
        newUserEntity.setEncryptedPassword("12345678");


        Assertions.assertThrows(PersistenceException.class, () -> {entityManager.persistAndFlush(newUserEntity);});

    }
}
