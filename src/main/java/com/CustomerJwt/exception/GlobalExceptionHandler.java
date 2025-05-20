package com.CustomerJwt.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFound(CustomerNotFoundException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now());

        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "CustomerNotFound");
        error.put("message", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

}
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGlobalException(Exception ex){

        Map<String,Object> error = new HashMap<>();

        error.put("timestamp",LocalDateTime.now());

        error.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());

        error.put("error ","Internal Server error");

        error.put("message", ex.getMessage());

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
