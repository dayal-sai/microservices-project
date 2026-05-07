package com.auth.server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	
@ExceptionHandler(UserValidationException.class)	
public ResponseEntity<String> UserValidation(UserValidationException ex)	{
	
	 return ResponseEntity.status(401).body(ex.getMessage());
}
	
	
	
}
