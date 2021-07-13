package com.intetics.java.task.jpa;

import com.intetics.java.task.entity.User;
import com.intetics.java.task.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserJpaTest {

    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1996, 7, 25);

    private final User user = User.builder()
            .firstName("Alexey")
            .lastName("Koltovich")
            .dateOfBirth(DATE_OF_BIRTH)
            .travels(null)
            .build();

    private final User checkedUser = User.builder()
            .id(1L)
            .firstName("Alexey")
            .lastName("Koltovich")
            .dateOfBirth(DATE_OF_BIRTH)
            .travels(null)
            .build();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void fill() {
        entityManager.persistAndFlush(user);
    }

    @AfterEach
    void cleanup() {
        entityManager.clear();
    }

    @Test
    public void shouldGetAllUsersFromDatabase() {
        List<User> users = userRepository.findAll();
        assertEquals(Collections.singletonList(checkedUser), users);
    }

    @Test
    public void shouldGetUserById() {
        Optional<User> user = userRepository.findById(1L);
        Optional<User> user1 = Optional.ofNullable(checkedUser);
        assertEquals(user1, user);
    }

    @Test
    public void shouldAddUserToDatabase() {
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        assertEquals(Collections.singletonList(user), users);
    }

    @Test
    public void shouldDeleteUserFromDatabase() {
        userRepository.deleteById(1L);
        List<User> users = userRepository.findAll();
        assertTrue(users.isEmpty());
    }
}
