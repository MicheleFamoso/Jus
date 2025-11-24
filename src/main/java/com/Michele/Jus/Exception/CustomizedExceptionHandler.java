package com.Michele.Jus.Exception;


import com.Michele.Jus.Model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomizedExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //stato risposta
    public ApiError notFoundExceptionHandler(NotFoundException e) {

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());

        return error;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError ValidationExceptionHandler(ValidationException e){

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return  error;
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError UnAuthorizedExceptionHandler(UnAuthorizedException e){

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return  error;
    }
}
