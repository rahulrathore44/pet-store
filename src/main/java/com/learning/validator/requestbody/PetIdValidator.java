package com.learning.validator.requestbody;

import com.learning.exceptions.InvalidPetIdException;
import com.learning.validator.Validator;

public class PetIdValidator implements Validator<Integer> {

    @Override
    public void validate(Integer id) {
        if (id <= 0) {
            throw new InvalidPetIdException("Invalid pet ID: " + id + ". Pet ID must be a positive integer.");
        }
    }
}
