package com.nikki.ecomm.exceptions;


import com.nikki.ecomm.payload.APIResponse;
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
    public ResponseEntity<APIResponse> hanldeResourceNotFound(ResourceNotFound e) {
        String result  = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(result,false));
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> handelAPIException(APIException e) {
        String result  = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(result,false));
    }
}
