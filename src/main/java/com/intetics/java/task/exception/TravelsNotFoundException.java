package com.intetics.java.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Travels not found")
public class TravelsNotFoundException extends RuntimeException{
    public TravelsNotFoundException(Long id) {
        super("Travels with id " + id + " not found.");
    }
}
