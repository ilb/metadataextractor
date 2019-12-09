package ru.ilb.metadataextractor.exceptions;

@SuppressWarnings("serial")
public class CreateObjectError extends RuntimeException {

    public CreateObjectError(String message) {
        super(message);
    }

    public CreateObjectError(String message, Throwable cause) {
        super(message, cause);
    }

}
