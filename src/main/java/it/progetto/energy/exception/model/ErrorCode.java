package it.progetto.energy.exception.model;

public interface ErrorCode {

    String BAD_REQUEST_ERROR_CODE = "1000";

    String getErrorCode();

    String getErrorMessage();

    static ErrorCode buildBadRequestErrorCode(final String fieldName, final String errorMessage) {
        return new ErrorCode() {
            public String getErrorCode() {
                return BAD_REQUEST_ERROR_CODE;
            }

            public String getErrorMessage() {
                return String.format("Error on input params: %s %s", fieldName, errorMessage);
            }
        };
    }
}
