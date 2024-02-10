package it.progetto.energy.exception;

import it.progetto.energy.exception.handler.APIException;
import it.progetto.energy.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.List;

public class NotCreatableException extends APIException {

    private static final HttpStatus httpStatus;

    static {
        httpStatus = HttpStatus.BAD_REQUEST;
    }

    public NotCreatableException(String message, List<ErrorCode> errorCodeList) {
        super(message, httpStatus, errorCodeList);
    }

    public NotCreatableException(String message, List<ErrorCode> errorCodeList, Throwable throwable) {
        super(message, httpStatus, errorCodeList, throwable);
    }

    public NotCreatableException(Throwable throwable, List<ErrorCode> errorCodeList) {
        this(throwable.getMessage(), errorCodeList, throwable);
    }

    public NotCreatableException(ErrorCode errorCode) {
        this(errorCode.getErrorMessage(), List.of(errorCode));
    }

}
