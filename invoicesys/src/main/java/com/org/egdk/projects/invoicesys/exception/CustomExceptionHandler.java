package com.org.egdk.projects.invoicesys.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(InvoiceNotFoundException.class)
	public final ResponseEntity<Object> handleValidationException(InvoiceNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(911l, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PaidamountGreterThanAmountException.class)
	public final ResponseEntity<Object> handleValidationException(PaidamountGreterThanAmountException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(912l, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
