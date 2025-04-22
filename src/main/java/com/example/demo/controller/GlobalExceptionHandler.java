package com.example.demo.controller;

import com.example.demo.controller.exception.DuplicateIsbnException;
import com.example.demo.controller.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handleNotFound(ResourceNotFoundException ex) {
    return Collections.singletonMap("error", ex.getMessage());
  }

  @ExceptionHandler(DuplicateIsbnException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleDuplicate(DuplicateIsbnException ex) {
    return Collections.singletonMap("error", ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, List<String>> handleValidation(MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(err -> errors.add(err.getField() + ": " + err.getDefaultMessage()));
    return Collections.singletonMap("validationErrors", errors);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleDataIntegrity(DataIntegrityViolationException ex) {
    return Collections.singletonMap("error",
        "Database error: " + ex.getMostSpecificCause().getMessage());
  }
}
