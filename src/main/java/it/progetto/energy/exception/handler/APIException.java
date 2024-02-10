package it.progetto.energy.exception.handler;

import it.progetto.energy.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class APIException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final List<ErrorCode> errorCodeList;

    protected APIException(Throwable throwable, HttpStatus httpStatus, List<ErrorCode> errorCodeList) {
        this(throwable.getMessage(), httpStatus, errorCodeList, throwable);
    }

    protected APIException(String errorMsg, HttpStatus httpStatus, List<ErrorCode> errorCodeList) {
        super(String.format("%s Errors detected: %s", errorMsg, toStringErrorCodeList(errorCodeList)));
        this.httpStatus = httpStatus;
        this.errorCodeList = errorCodeList;
    }

    protected APIException(String errorMsg, HttpStatus httpStatus, List<ErrorCode> errorCodeList, Throwable throwable) {
        super(String.format("%s Errors detected: %s", errorMsg, toStringErrorCodeList(errorCodeList)), throwable);
        this.httpStatus = httpStatus;
        this.errorCodeList = errorCodeList;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public List<ErrorCode> getErrorCodeList() {
        return this.errorCodeList;
    }

    private static String toStringErrorCodeList(List<ErrorCode> errorCodeList) {
        return errorCodeList.stream().map(errorCode ->
                        String.format("{ErrorCode: %s, ErrorMessage: %s}", errorCode.getErrorCode(), errorCode.getErrorMessage()))
                .reduce((errorMsg1, errorMsg2) -> String.join(",", errorMsg1, errorMsg2))
                .orElse(errorCodeList.toString());
    }

}
