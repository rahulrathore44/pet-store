package com.learning.exceptions;

public class InvalidPetStatus extends RuntimeException {

    public InvalidPetStatus(String message) {
        super(message);
    }

    public InvalidPetStatus(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPetStatus(Throwable cause) {
        super(cause);
    }

}
