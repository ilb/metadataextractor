package ru.ilb.metadataextractor.exceptions;

public class GetPropertyError extends RuntimeException {

    public GetPropertyError(String message) {
        super(message);
    }

    public GetPropertyError(String message, Throwable cause) {
        super(message, cause);
    }

}
