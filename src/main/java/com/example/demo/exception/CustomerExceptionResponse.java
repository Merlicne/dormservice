package com.example.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionResponse  {


    @ExceptionHandler(NotFoundException.class)
    public ErrorResponseBody handleNotFoundException(NotFoundException ex) {
        return ErrorResponseBody.builder().status(404).message(ex.getMessage()).build();
    }
    
    @ExceptionHandler(ForbiddenException.class)
    public ErrorResponseBody handleForbiddenException(ForbiddenException ex) {
        return ErrorResponseBody.builder().status(403).message(ex.getMessage()).build();
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ErrorResponseBody handleUnAuthorizedException(UnAuthorizedException ex) {
        return ErrorResponseBody.builder().status(401).message(ex.getMessage()).build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ErrorResponseBody handleBadRequestException(BadRequestException ex) {
        return ErrorResponseBody.builder().status(400).message(ex.getMessage()).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseBody handleRuntimeException(RuntimeException ex) {
        return ErrorResponseBody.builder().status(500).message("Unexpected Error ::"+ex.getMessage()).build();
    }
}
