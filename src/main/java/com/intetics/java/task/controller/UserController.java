package com.intetics.java.task.controller;

import com.intetics.java.task.dto.UserDto;
import com.intetics.java.task.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping
    @ApiOperation("Get all users from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public List<UserDto> getAllUsers() {
        log.info("Calling get all users");
        return userService.getAllUsers();
    }

    @GetMapping("/id")
    @ApiOperation("Get user by id from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public UserDto getUserById(@Positive @RequestParam Long id) {
        log.info("Calling get user with id {}", id);
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Add user to database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully add user to database"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public void addUser(@Valid @RequestBody UserDto user) {
        log.info("Calling add user to database with fields {}", user);
        userService.addUser(user);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update user in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update user"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public void updateUser(@Valid @RequestBody UserDto user, @Positive @PathVariable Long id){
        log.info("Calling update user with id {} by fields {}", id, user);
        userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete user from database")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully delete user"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public void deleteUser(@Positive @PathVariable Long id) {
        log.info("Calling delete user with id {}", id);
        userService.deleteUser(id);
    }
}
