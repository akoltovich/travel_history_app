package com.intetics.java.task.service;

import com.intetics.java.task.dto.TravelDto;
import com.intetics.java.task.entity.Travel;
import com.intetics.java.task.exception.TravelsNotFoundException;
import com.intetics.java.task.exception.UsersNotFoundException;
import com.intetics.java.task.repository.TravelRepository;
import com.intetics.java.task.repository.UserRepository;
import com.intetics.java.task.util.WeatherSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelService {

    private final TravelRepository travelRepository;
    private final UserRepository userRepository;

    public List<TravelDto> getAllTravels() {
        return travelRepository.findAll().stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public TravelDto getTravelById(Long id) {
        return travelRepository.findById(id)
                .map(this::fromEntityToDto)
                .orElseThrow(() -> new TravelsNotFoundException(id));
    }

    public void addTravel(TravelDto travel) {
        if (!userRepository.existsById(travel.getUserId())) {
            log.warn("User with id {} does not exist", travel.getUserId());
            throw new UsersNotFoundException(travel.getUserId());
        } else {
            travelRepository.save(fromDtoToEntity(travel));
            log.info("Travel with fields {} was added to database", travel);
        }
    }

    public void deleteTravel(Long id) {
        if (!travelRepository.existsById(id)) {
            log.warn("Can't delete travel with id {} cause: does not exist", id);
            throw new TravelsNotFoundException(id);
        } else {
            travelRepository.deleteById(id);
            log.info("Travel with id {} was successfully deleted", id);
        }
    }

    public void updateTravel(TravelDto travel, Long id) {
        if (travelRepository.existsById(id)) {
            travelRepository.save(Travel.builder()
                    .id(id)
                    .country(travel.getCountry())
                    .yearOfTravel(travel.getYearOfTravel())
                    .weather(travel.getWeather())
                    .description(travel.getDescription())
                    .user(userRepository.findById(travel.getUserId()).get())
                    .build());
            log.info("Travel with id {} was updated", id);
        } else {
            log.warn("Travel with id {} does not exist in database", id);
            throw new TravelsNotFoundException(id);
        }
    }

    private TravelDto fromEntityToDto(Travel travel) {
        return TravelDto.builder()
                .id(travel.getId())
                .country(travel.getCountry())
                .yearOfTravel(travel.getYearOfTravel())
                .weather(WeatherSearch.findWeatherByCountryName(travel.getCountry()))
                .description(travel.getDescription())
                .userId(travel.getUser().getId())
                .build();
    }

    private Travel fromDtoToEntity(TravelDto travelDto) {
        return Travel.builder()
                .country(travelDto.getCountry())
                .yearOfTravel(travelDto.getYearOfTravel())
                .weather(WeatherSearch.findWeatherByCountryName(travelDto.getCountry()))
                .description(travelDto.getDescription())
                .user(userRepository.getOne(travelDto.getUserId()))
                .build();
    }
}
