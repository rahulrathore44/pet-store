package com.learning.exceptions;

public class InvalidPetIdException extends RuntimeException {

    public InvalidPetIdException(String message) {
        super(message);
    }

    public InvalidPetIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPetIdException(Throwable cause) {
        super(cause);
    }

}
