package com.nikki.ecomm.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HashMap<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldError = ((FieldError)error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldError, errorMessage);
                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> hanldeResourceNotFound(ResourceNotFound e) {
        String result  = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> handelAPIException(APIException e) {
        String result  = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
