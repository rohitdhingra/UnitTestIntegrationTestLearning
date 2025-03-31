package com.learning.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepository usersRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        String userId = UUID.randomUUID().toString();
        userEntity.setUserId(userId);
        userEntity.setFirstName("John");
        userEntity.setLastName("Smith");
        userEntity.setEmail("john.smith@gmail.com");
        userEntity.setEncryptedPassword("password");
        entityManager.persistAndFlush(userEntity);
    }

    @Test
    void testFindByEmail_whenGivenCorrectEmail_shouldReturnUserEntity() {
        //Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("John");
        userEntity.setLastName("Smith");
        userEntity.setEmail("john.smith@gmail.com");
        userEntity.setEncryptedPassword("password");
        entityManager.persistAndFlush(userEntity);

        //Act
        UserEntity storedUserEntity = usersRepository.findByEmail(userEntity.getEmail());

        //Assert
        Assertions.assertEquals(userEntity.getUserId(), storedUserEntity.getUserId());
        Assertions.assertEquals(userEntity.getEmail(), storedUserEntity.getEmail());
    }

    @Test
    void testFindByUserId_whenGivenCorrectUserId_shouldReturnUserEntity() {
        //Arrange
        UserEntity userEntity = new UserEntity();
        String userId = UUID.randomUUID().toString();
        userEntity.setUserId(userId);
        userEntity.setFirstName("John");
        userEntity.setLastName("Smith");
        userEntity.setEmail("john.smith@gmail.com");
        userEntity.setEncryptedPassword("password");
        entityManager.persistAndFlush(userEntity);

        //Act
        UserEntity storedUserEntity = usersRepository.findByUserId(userEntity.getUserId());

        //Assert
        Assertions.assertEquals(userEntity.getUserId(), storedUserEntity.getUserId());
//        Assertions.assertEquals(userEntity.getEmail(), storedUserEntity.getEmail());
    }

    @Test
    void testFindUsersWithEmailEndsWith_whenGivenDomainName_shouldReturnUserEntity() {
        //Arrange
        
        //Act
        List<UserEntity> userEntityList = usersRepository.findUsersWithEmailEndingWith("@gmail.com");
        //Assert
        Assertions.assertTrue( userEntityList.size()>0);
    }
}
