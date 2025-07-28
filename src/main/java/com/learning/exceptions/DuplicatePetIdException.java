package com.learning.exceptions;

public class DuplicatePetIdException extends RuntimeException {
    public DuplicatePetIdException(String message) {
        super(message);
    }
}

