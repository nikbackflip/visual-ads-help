package com.backflip.vadsh.controller.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse badRequestException(BadRequestException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
