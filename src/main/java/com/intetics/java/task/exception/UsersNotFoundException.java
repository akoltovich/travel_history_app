package com.intetics.java.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Users not found")
public class UsersNotFoundException extends RuntimeException {
    public UsersNotFoundException(Long id) {
        super("Users with id " + id + " not found.");
    }
}
