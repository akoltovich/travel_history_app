package com.intetics.java.task.service;

import com.intetics.java.task.dto.UserDto;
import com.intetics.java.task.entity.User;
import com.intetics.java.task.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1996, 7, 25);

    private static final User USER = User.builder()
            .id(1L)
            .firstName("Alexey")
            .lastName("Koltovich")
            .dateOfBirth(DATE_OF_BIRTH)
            .travels(null)
            .build();

    private static final UserDto USER_DTO = UserDto.builder()
            .id(1L)
            .firstName("Alexey")
            .lastName("Koltovich")
            .dateOfBirth(DATE_OF_BIRTH)
            .travels(null)
            .build();

    private static final UserDto USER_WITHOUT_ID = UserDto.builder()
            .firstName("Alexey")
            .lastName("Koltovich")
            .dateOfBirth(DATE_OF_BIRTH)
            .travels(null)
            .build();

    private static final List<User> USER_LIST = new ArrayList<>();
    private static final List<UserDto> USER_DTO_LIST = new ArrayList<>();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @AfterEach
    void check() {
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldGetAllUsersFromDatabase() {
        USER_LIST.add(USER);
        USER_DTO_LIST.add(USER_DTO);

        when(userRepository.findAll()).thenReturn(USER_LIST);

        List<UserDto> allUsers = userService.getAllUsers();

        verify(userRepository).findAll();
        assertEquals(USER_DTO_LIST, allUsers);
    }

    @Test
    public void shouldGetUserById() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(USER));

        UserDto travelById = userService.getById(1L);
        verify(userRepository).findById(1L);
        assertEquals(USER_DTO, travelById);
    }

    @Test
    public void shouldAddUserToDatabase() {
        ArgumentCaptor<User> userEntityCaptor = ArgumentCaptor.forClass(User.class);

        userService.addUser(USER_WITHOUT_ID);

        verify(userRepository).save(userEntityCaptor.capture());
        User actualUserEntity = userEntityCaptor.getValue();
        assertEquals("Alexey", actualUserEntity.getFirstName());
        assertEquals("Koltovich", actualUserEntity.getLastName());
        assertEquals(DATE_OF_BIRTH, actualUserEntity.getDateOfBirth());
        assertNull(actualUserEntity.getTravels());
    }

    @Test
    public void shouldDeleteUserFromDatabase() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    public void shouldUpdateUserInDatabase() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.updateUser(USER_DTO, 1L);

        verify(userRepository).save(USER);
    }
}
