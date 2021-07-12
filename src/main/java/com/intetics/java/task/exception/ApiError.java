package com.intetics.java.task.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

    private HttpStatus Status;
    private String error;
}