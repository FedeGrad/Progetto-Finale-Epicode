package it.progetto.energy.exception.model;


// @JsonFormat(shape = JsonFormat.Shape.OBJECT) //this work, but we prefer to
// use mapper for uncoupling layers
public enum ErrorCodeDomain implements ErrorCode {
    ERROR_ONE("1000", "Big Big Problem Bro!"),
    ADDRESS_ALREADY_EXISTS("101", "Address already present"),
    ADDRESS_NOT_FOUND("102", "Address not found"),
    COMUNE_NOT_FOUND("103", "Comune not found"),
    COMUNE_ALREADY_EXISTS("104", "Comune already present"),
    PROVINCIA_NOT_FOUND("105", "Provincia not found"),
    INVALID_IMPUT_VALUE("106", "Email, Phone or npi not valid"),
    COMSTOMER_ALREADY_EXISTS("107", "Customer already present"),
    CUSTOMER_NOT_FOUND("108", "Customer not found"),
    ERROR_LOAD_FILE("109", "Could not possible to get or read file"),
    ERROR_INIT_FILE("110", "Could not possible create directory"),
    ERROR_SAVE_FILE("111", "Could not possibile save file"),
    INVOICE_NOT_FOUND("112", "Invoice not found"),
    PROVINCIA_ALREADY_PRESENT("113", "Provincia already present"),
    USERNAME_ALREADY_PRESENT("114", "Username already present"),
    USER_NOT_FOUND("115", "User not found");


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
