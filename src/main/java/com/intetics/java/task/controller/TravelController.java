package com.intetics.java.task.controller;

import com.intetics.java.task.dto.TravelDto;
import com.intetics.java.task.service.TravelService;
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
@RequestMapping("/travels")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TravelController {

    private final TravelService travelService;

    @GetMapping
    @ApiOperation("Get all travels from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved travel list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public List<TravelDto> getAllTravels() {
        log.info("Calling get all travels");
        return travelService.getAllTravels();
    }

    @GetMapping("/id")
    @ApiOperation("Get travel by id from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved travel"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public TravelDto getTravelById(@Positive @RequestParam Long id) {
        log.info("Calling get travel with id {}", id);
        return travelService.getTravelById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Add travel to database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully add travel to database"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public void addTravel(@RequestBody @Valid TravelDto travel) {
        log.info("Calling add travel {} to database", travel);
        travelService.addTravel(travel);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update travel in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update travel"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public void updateTravel(@RequestBody @Valid TravelDto travel, @Positive @PathVariable Long id) {
        log.info("Calling update travel with id {} by fields {}", id, travel);
        travelService.updateTravel(travel, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete travel from database")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully delete travel"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server not available")
    })
    public void deleteTravel(@Positive @PathVariable Long id) {
        log.info("Calling delete travel with id {}", id);
        travelService.deleteTravel(id);
    }
}
