package it.progetto.energy.exception;

import it.progetto.energy.exception.handler.APIException;
import it.progetto.energy.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ElementAlreadyPresentException extends APIException {

	private static final HttpStatus httpStatus;

	public ElementAlreadyPresentException(String message, List<ErrorCode> errorCodeList) {
		super(message, httpStatus, errorCodeList);
	}

	public ElementAlreadyPresentException(Throwable throwable, ErrorCode errorCode) {
		this(throwable.getMessage(), List.of(errorCode));
	}

	public ElementAlreadyPresentException(Throwable throwable, List<ErrorCode> errorCodeList) {
		this(throwable.getMessage(), errorCodeList);
	}

	public ElementAlreadyPresentException(ErrorCode errorCode) {
		this(errorCode.getErrorMessage(), List.of(errorCode));
	}

	static {
		httpStatus = HttpStatus.NOT_ACCEPTABLE;
	}

	
}
