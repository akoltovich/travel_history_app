package com.intetics.java.task.controller;

import com.intetics.java.task.dto.TravelDto;
import com.intetics.java.task.service.TravelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TravelController.class)
public class TravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelService travelService;

    private static final String MAIN_MAPPING = "/travels";

    private static final TravelDto TRAVEL = TravelDto.builder()
            .id(1L)
            .country("Germany")
            .yearOfTravel(1990)
            .weather("Some weather")
            .description("Some description")
            .userId(1L)
            .build();

    private static final TravelDto TRAVEL_WITHOUT_ID = TravelDto.builder()
            .country("Germany")
            .yearOfTravel(1990)
            .weather("Some weather")
            .description("Some description")
            .userId(1L)
            .build();

    private static final TravelDto UPDATED_TRAVEL = TravelDto.builder()
            .country("Russia")
            .yearOfTravel(2000)
            .weather("Other weather")
            .description("Other description")
            .userId(1L)
            .build();

    private static final List<TravelDto> TRAVEL_LIST = new ArrayList<>();

    private static final String TRAVEL_JSON = "{\"id\":1,\"country\":\"Germany\",\"yearOfTravel\":1990,\"weather\":\"Some weather\"," +
            "\"description\":\"Some description\",\"userId\":1}";
    private static final String TRAVEL_JSON_WITHOUT_ID = "{\"country\":\"Germany\",\"yearOfTravel\":1990,\"weather\":\"Some weather\"," +
            "\"description\":\"Some description\",\"userId\":1}";
    private static final String UPDATED_TRAVEL_JSON_WITHOUT_ID = "{\"country\":\"Russia\",\"yearOfTravel\":2000,\"weather\":\"Other weather\"," +
            "\"description\":\"Other description\",\"userId\":1}";
    private static final String TRAVEL_LIST_JSON = "[{\"id\":1,\"country\":\"Germany\",\"yearOfTravel\":1990,\"weather\":\"Some weather\"," +
            "\"description\":\"Some description\",\"userId\":1}]";
    private static final String INVALID_TRAVEL_JSON = "{\"country\":\"Gemany\",\"yearOfTravel\":1990,\"weather\":\"Some weather\"," +
            "\"description\":\"Some description\",\"userId\":1}";

    @BeforeEach
    void fillList() {
        TRAVEL_LIST.add(TRAVEL);
    }

    @AfterEach
    void cleanList() {
        TRAVEL_LIST.remove(0);
    }

    @Test
    public void shouldGetAllTravelsFromDatabase() throws Exception {
        when(travelService.getAllTravels()).thenReturn(TRAVEL_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get(MAIN_MAPPING)
                .content(String.valueOf(TRAVEL_LIST))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(TRAVEL_LIST_JSON));
        verify(travelService).getAllTravels();
    }

    @Test
    public void shouldGetTravelFromDatabaseById() throws Exception {
        when(travelService.getTravelById(1L)).thenReturn(TRAVEL);

        mockMvc.perform(MockMvcRequestBuilders.get(MAIN_MAPPING + "/id?id=1")
                .content(String.valueOf(TRAVEL))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(TRAVEL_JSON));
        verify(travelService).getTravelById(1L);
    }

    @Test
    public void shouldAddTravelToDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(MAIN_MAPPING)
                .accept(MediaType.APPLICATION_JSON)
                .content(TRAVEL_JSON_WITHOUT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        verify(travelService).addTravel(TRAVEL_WITHOUT_ID);
    }

    @Test
    public void shouldDeleteTravelFromDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(MAIN_MAPPING + "/1"))
                .andExpect(status().isNoContent());
        verify(travelService).deleteTravel(1L);
    }

    @Test
    public void shouldUpdateTravelInformation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(MAIN_MAPPING + "/1")
                .content(UPDATED_TRAVEL_JSON_WITHOUT_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(travelService).updateTravel(UPDATED_TRAVEL, 1L);
    }

    @Test
    public void shouldFailedValidationWhetTryToAddTravelToDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(MAIN_MAPPING)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(INVALID_TRAVEL_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
    }
}
