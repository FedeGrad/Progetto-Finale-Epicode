package it.progetto.energy.exception.model;


// @JsonFormat(shape = JsonFormat.Shape.OBJECT) //this work, but we prefer to
// use mapper for uncoupling layers
public enum ErrorCodeDomain implements ErrorCode {
    ERROR_ONE("100", ""),
    ERROR_TWO("101", "");


    private final String errorCode;
    private final String errorMessage;

    ErrorCodeDomain(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
