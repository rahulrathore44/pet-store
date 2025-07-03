package com.learning.validator;

import com.learning.dto.Pet;
import com.learning.validator.requestbody.PetIdValidator;
import com.learning.validator.requestbody.StatusValidator;
import jakarta.inject.Singleton;

@Singleton
public class ValidationContext {

    private final Validator<Integer> petIdValidator;
    private final Validator<String> petStatusValidator;

    public ValidationContext() {
        this.petIdValidator = new PetIdValidator();
        this.petStatusValidator = new StatusValidator();
    }

    public Validator<Integer> getPetIdValidator() {
        return petIdValidator;
    }

    public Validator<String> getPetStatusValidator() {
        return petStatusValidator;
    }
}
