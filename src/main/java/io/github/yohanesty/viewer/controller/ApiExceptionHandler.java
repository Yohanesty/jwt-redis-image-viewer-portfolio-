package io.github.yohanesty.viewer.controller;

import io.github.yohanesty.viewer.service.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, String>> forbidden(ForbiddenException e) {
        return new ResponseEntity<Map<String, String>>(
                Collections.singletonMap("error", e.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e) {
        return new ResponseEntity<Map<String, String>>(
                Collections.singletonMap("error", e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
