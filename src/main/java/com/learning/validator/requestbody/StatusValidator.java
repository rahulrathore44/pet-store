package com.learning.validator.requestbody;

import com.learning.dto.Status;
import com.learning.exceptions.InvalidPetStatusException;
import com.learning.validator.Validator;

import java.util.Arrays;

public class StatusValidator implements Validator<String> {

    @Override
    public void validate(String status) {
        try {
            Status.valueOf(status);
        } catch (Exception e) {
            throw new InvalidPetStatusException("Invalid pet status: " + status + ". Valid values are: " + Arrays.toString(Status.values()));
        }
    }
}
