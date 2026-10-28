package com.fooddelivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FoodDeliveryException.class)
    public ResponseEntity<Map<String, Object>> handleFoodDeliveryException(FoodDeliveryException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now().toString());
        errorDetails.put("status", "ERROR");
        errorDetails.put("exceptionType", ex.getClass().getSimpleName());
        errorDetails.put("message", ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}