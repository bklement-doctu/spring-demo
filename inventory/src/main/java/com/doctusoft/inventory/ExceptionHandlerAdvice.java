package com.doctusoft.inventory;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(BookNotAvailableException.class)
	public ResponseEntity<String> handleItemNotAvailableException(BookNotAvailableException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Book not available with id " + e.getBookId());
	}
	
}
