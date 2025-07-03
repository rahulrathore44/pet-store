package com.learning.validator;

public interface Validator<T> {

    void validate(T object);
}
