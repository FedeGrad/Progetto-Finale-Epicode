package it.progetto.energy.exception;

import it.progetto.energy.exception.handler.APIException;
import it.progetto.energy.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.List;

public class NotUpdatableException extends APIException {

    private static final HttpStatus httpStatus;

    static {
        httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public NotUpdatableException(String message, List<ErrorCode> errorCodeList) {
        super(message, httpStatus, errorCodeList);
    }

    public NotUpdatableException(String message, List<ErrorCode> errorCodeList, Throwable throwable) {
        super(message, httpStatus, errorCodeList, throwable);
    }

    public NotUpdatableException(Throwable throwable, List<ErrorCode> errorCodeList) {
        this(throwable.getMessage(), errorCodeList, throwable);
    }

    public NotUpdatableException(ErrorCode errorCode) {
        this(errorCode.getErrorMessage(), List.of(errorCode));
    }

}
