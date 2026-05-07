package com.order.service.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> productNotFoundException(ResourceNotFoundException ex){
		
        return ResponseEntity.status(404).body(ex.getMessage());


	}
	
	@ExceptionHandler(ServiceDownException.class)
	public ResponseEntity serviceNotFound(ServiceDownException ex) {
	
		return ResponseEntity.status(500).body(ex.getMessage());		
		
		
	}
	

	
	
	
	
}
