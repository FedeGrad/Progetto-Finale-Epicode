package it.progetto.energy.dto;

public record ErrorDTO(String code, String message) {

    public ErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
