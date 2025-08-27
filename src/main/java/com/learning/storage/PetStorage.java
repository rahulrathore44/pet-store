package com.learning.storage;

import com.learning.dto.Pet;
import io.micronaut.context.annotation.Prototype;

import java.util.HashMap;
import java.util.Map;

@Prototype
public class PetStorage {

    public Map<Integer, Pet> getPetStore() {
        return petStore;
    }

    private final Map<Integer, Pet> petStore;

    public PetStorage() {
        this.petStore = new HashMap<>();
    }

}