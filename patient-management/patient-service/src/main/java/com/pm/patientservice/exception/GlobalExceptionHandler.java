package com.pm.patientservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j; 

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handlePatientExists(PatientAlreadyExistsException ex) {
        log.warn("Patient already exists: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Email already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error); 
    }
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFound(PatientNotFoundException ex) {
        log.warn("Patient not found: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Patient not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); 
    }
}
