package com.intetics.java.task.service;

import com.intetics.java.task.dto.TravelDto;
import com.intetics.java.task.dto.UserDto;
import com.intetics.java.task.entity.Travel;
import com.intetics.java.task.entity.User;
import com.intetics.java.task.exception.UsersNotFoundException;
import com.intetics.java.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id).
                map(this::fromEntityToDto)
                .orElseThrow(() -> new UsersNotFoundException(id));
    }

    public void addUser(UserDto user) {
        userRepository.save(fromDtoToEntity(user));
        log.info("User with fields {} was added to database", user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            log.warn("Can't delete user with id '{}' cause: does not exist", id);
            throw new UsersNotFoundException(id);
        } else {
            userRepository.deleteById(id);
            log.info("User with id {} was successfully deleted", id);
        }
    }

    public void updateUser(UserDto user, Long id) {
        if (userRepository.existsById(id)) {
            userRepository.save(User.builder()
                    .id(id)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
//                    .travels(fromListOfDtoToListOfEntities(user.getTravels()))
                    .build());
            log.info("User with id {} was successfully updated", id);
        } else {
            log.warn("User with id {} does not exist", id);
            throw new UsersNotFoundException(id);
        }
    }

    private UserDto fromEntityToDto(User user) {
        UserDto userDto;
        if (user.getTravels() == null) {
            userDto = UserDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .travels(null)
                    .build();
        } else {
            userDto = UserDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .travels(fromListOfEntitiesToListOfDto(user.getTravels()))
                    .build();
        }
        return userDto;
    }

    private User fromDtoToEntity(UserDto userDto) {
        User user;
        if (userDto.getTravels() == null) {
            user = User.builder()
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .dateOfBirth(userDto.getDateOfBirth())
                    .travels(null)
                    .build();
        } else {
            user = User.builder()
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .dateOfBirth(userDto.getDateOfBirth())
                    .travels(fromListOfDtoToListOfEntities(userDto.getTravels()))
                    .build();
        }
        return user;
    }

    private List<TravelDto> fromListOfEntitiesToListOfDto(List<Travel> travels) {
        List<TravelDto> newList = new ArrayList<>();
        for (Travel t : travels) {
            newList.add(TravelDto.builder()
                    .id(t.getId())
                    .country(t.getCountry())
                    .yearOfTravel(t.getYearOfTravel())
                    .weather(t.getWeather())
                    .description(t.getDescription())
                    .userId(t.getUser().getId())
                    .build());

        }
        return newList;
    }

    private List<Travel> fromListOfDtoToListOfEntities(List<TravelDto> travels) {
        List<Travel> newList = new ArrayList<>();
        for (TravelDto t : travels) {
            newList.add(Travel.builder()
                    .id(t.getId())
                    .country(t.getCountry())
                    .yearOfTravel(t.getYearOfTravel())
                    .weather(t.getWeather())
                    .description(t.getDescription())
                    .user(userRepository.findById(t.getUserId()).get())
                    .build());

        }
        return newList;
    }
}
