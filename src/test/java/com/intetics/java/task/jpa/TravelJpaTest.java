package com.intetics.java.task.jpa;

import com.intetics.java.task.entity.Travel;
import com.intetics.java.task.repository.TravelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TravelJpaTest {


    private final Travel travel = Travel.builder()
            .country("Germany")
            .yearOfTravel(1990)
            .weather("Some weather")
            .description("Some description")
            .build();

    private final Travel checkedTravel = Travel.builder()
            .id(1L)
            .country("Germany")
            .yearOfTravel(1990)
            .weather("Some weather")
            .description("Some description")
            .build();

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void fill() {
        entityManager.persistAndFlush(travel);
    }

    @AfterEach
    void cleanup() {
        entityManager.clear();
    }

    @Test
    public void shouldGetAllTravelsFromDatabase() {
        List<Travel> travels = travelRepository.findAll();
        assertEquals(Collections.singletonList(checkedTravel), travels);
    }

    @Test
    public void shouldGetTravelById() {
        Optional<Travel> travel = travelRepository.findById(1L);
        Optional<Travel> travel1 = Optional.ofNullable(checkedTravel);
        assertEquals(travel1, travel);
    }

    @Test
    public void shouldAddTravelToDatabase() {
        travelRepository.save(travel);
        List<Travel> travels = travelRepository.findAll();
        assertEquals(Collections.singletonList(travel), travels);
    }

    @Test
    public void shouldDeleteTravelFromDatabase() {
        travelRepository.deleteById(1L);
        List<Travel> travels = travelRepository.findAll();
        assertTrue(travels.isEmpty());
    }
}
