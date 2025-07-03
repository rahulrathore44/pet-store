package com.learning.exceptions;

public class InvalidPetStatusException extends RuntimeException {

    public InvalidPetStatusException(String message) {
        super(message);
    }

    public InvalidPetStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPetStatusException(Throwable cause) {
        super(cause);
    }

}
