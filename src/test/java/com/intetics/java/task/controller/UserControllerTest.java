package com.intetics.java.task.controller;

import com.intetics.java.task.dto.UserDto;
import com.intetics.java.task.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1996, 7, 25);

    private static final String MAIN_MAPPING = "/users";

    private static final UserDto USER = UserDto.builder()
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

    private static final UserDto UPDATED_USER = UserDto.builder()
            .firstName("NoAlexey")
            .lastName("NoKoltovich")
            .dateOfBirth(DATE_OF_BIRTH)
            .travels(null)
            .build();

    private static final List<UserDto> USER_LIST = new ArrayList<>();

    private static final String USER_JSON = "{\"id\":1,\"firstName\":\"Alexey\",\"lastName\":\"Koltovich\",\"dateOfBirth\":\"1996-07-25\",\"travels\":null}";
    private static final String USER_JSON_WITHOUT_ID = "{\"firstName\":\"Alexey\",\"lastName\":\"Koltovich\",\"dateOfBirth\":\"1996-07-25\",\"travels\":null}";
    private static final String UPDATED_USER_JSON_WITHOUT_ID = "{\"firstName\":\"NoAlexey\",\"lastName\":\"NoKoltovich\",\"dateOfBirth\":\"1996-07-25\",\"travels\":null}";
    private static final String USER_LIST_JSON = "[{\"id\":1,\"firstName\":\"Alexey\",\"lastName\":\"Koltovich\",\"dateOfBirth\":\"1996-07-25\",\"travels\":null}]";
    private static final String INVALID_USER_JSON = "{\"firstName\":\"A\",\"lastName\":\"Koltovich\",\"dateOfBirth\":\"1996-07-25\",\"travels\":null}";

    @BeforeEach
    void fillList() {
        USER_LIST.add(USER);
    }

    @AfterEach
    void cleanList() {
        USER_LIST.remove(0);
    }

    @Test
    public void shouldGetAllTravelsFromDatabase() throws Exception {
        when(userService.getAllUsers()).thenReturn(USER_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get(MAIN_MAPPING)
                .content(String.valueOf(USER_LIST))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(USER_LIST_JSON));
        verify(userService).getAllUsers();
    }

    @Test
    public void shouldGetTravelFromDatabaseById() throws Exception {
        when(userService.getById(1L)).thenReturn(USER);

        mockMvc.perform(MockMvcRequestBuilders.get(MAIN_MAPPING + "/id?id=1")
                .content(String.valueOf(USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(USER_JSON));
        verify(userService).getById(1L);
    }

    @Test
    public void shouldAddTravelToDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(MAIN_MAPPING)
                .accept(MediaType.APPLICATION_JSON)
                .content(USER_JSON_WITHOUT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        verify(userService).addUser(USER_WITHOUT_ID);
    }

    @Test
    public void shouldDeleteTravelFromDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(MAIN_MAPPING + "/1"))
                .andExpect(status().isNoContent());
        verify(userService).deleteUser(1L);
    }

    @Test
    public void shouldUpdateTravelInformation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(MAIN_MAPPING + "/1")
                .content(UPDATED_USER_JSON_WITHOUT_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(userService).updateUser(UPDATED_USER, 1L);
    }

    @Test
    public void shouldFailedValidationWhetTryToAddTravelToDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(MAIN_MAPPING)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(INVALID_USER_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
    }
}
