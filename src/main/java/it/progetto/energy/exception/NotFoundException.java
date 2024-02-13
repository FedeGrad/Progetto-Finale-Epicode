package it.progetto.energy.exception;

import it.progetto.energy.exception.handler.APIException;
import it.progetto.energy.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.List;

public class NotFoundException extends APIException {

    private static final HttpStatus httpStatus;

    static {
        httpStatus = HttpStatus.NOT_FOUND;
    }

    public NotFoundException(String message, List<ErrorCode> errorCodeList) {
        super(message, httpStatus, errorCodeList);
    }

    public NotFoundException(Throwable throwable, ErrorCode errorCode) {
        this(throwable.getMessage(), List.of(errorCode));
    }

    public NotFoundException(Throwable throwable, List<ErrorCode> errorCodeList) {
        this(throwable.getMessage(), errorCodeList);
    }

    public NotFoundException(ErrorCode errorCode) {
        this(errorCode.getErrorMessage(), List.of(errorCode));
    }

}
