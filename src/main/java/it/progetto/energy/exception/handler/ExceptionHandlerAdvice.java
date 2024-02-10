package it.progetto.energy.exception.handler;

import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotDeletableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.exception.model.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ElementAlreadyPresentException.class)
	public ResponseEntity<List<ErrorCode>> handleException(ElementAlreadyPresentException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorCodeList());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<List<ErrorCode>> handleException(NotFoundException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorCodeList());
	}
	
	@ExceptionHandler(NotCreatableException.class)
	public ResponseEntity<List<ErrorCode>> handleException(NotCreatableException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorCodeList());
	}

	@ExceptionHandler(NotUpdatableException.class)
	public ResponseEntity<List<ErrorCode>> handleException(NotUpdatableException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorCodeList());
	}

	@ExceptionHandler(NotDeletableException.class)
	public ResponseEntity<List<ErrorCode>> handleException(NotDeletableException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorCodeList());
	}


}
