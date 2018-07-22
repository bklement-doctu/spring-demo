package com.doctusoft.book;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
		StringBuilder builder = new StringBuilder("Bad request:");
		BindingResult bindingResult = e.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (int i = 0; i < bindingResult.getFieldErrorCount(); i++) {
			FieldError error = fieldErrors.get(i);
			builder.append(" ");
			builder.append(error.getField());
			builder.append(" ");
			builder.append(error.getDefaultMessage());
			builder.append(",");
		}
		builder.setCharAt(builder.length() - 1, '.');
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		Throwable cause = e;
		while (cause.getCause() != null) {
			cause = cause.getCause();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cause.getMessage().substring(0, cause.getMessage().indexOf(']') + 1));
	}
	
}
