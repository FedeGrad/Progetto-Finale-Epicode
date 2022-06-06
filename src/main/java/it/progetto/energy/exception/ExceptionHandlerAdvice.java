package it.progetto.energy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ElementAlreadyPresentException.class)
	public ResponseEntity entityAlreadyFound(ElementAlreadyPresentException ex) {
		return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(WrongInsertException.class)
	public ResponseEntity wrongInserData(WrongInsertException ex) {
		return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity notFoundException(NotFoundException ex) {
		return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	

}
