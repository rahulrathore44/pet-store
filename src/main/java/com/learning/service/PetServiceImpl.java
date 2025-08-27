package com.learning.service;

import com.learning.dto.Pet;
import com.learning.exceptions.DuplicatePetIdException;
import com.learning.storage.PetStorage;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class PetServiceImpl implements PetService {

    private final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);
    private final Map<Integer, Pet> petStore;

    @Inject
    public PetServiceImpl(PetStorage storage) {
        this.petStore = storage.getPetStore();
    }

    @Override
    public List<Pet> getAllPets() {
        return petStore.values().stream().toList();
    }

    @Override
    public Pet addPet(Pet pet) {
        if (petStore.containsKey(pet.getId())) {
            throw new DuplicatePetIdException("Pet with ID " + pet.getId() + " already exists");
        }
        petStore.put(pet.getId(), pet);
        logger.info("Added pet with ID: {}, and details: {}", pet.getId(), pet);
        return pet;
    }

    @Override
    public Pet updatePet(Pet pet) {
        if (!petStore.containsKey(pet.getId())) {
            logger.error("Pet with ID {} not found for update ", pet.getId());
            throw new NoSuchElementException("Pet not found for: " + pet);
        }
        petStore.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Pet updatePet(int id, String name, String status) {
        Pet pet = petStore.computeIfPresent(id, (key, existingPet) -> {
            existingPet.setName(name);
            existingPet.setStatus(status);
            return existingPet;
        });
        if (pet == null) {
            var errorMsg = "Pet with ID " + id + " not found for update";
            logger.error(errorMsg);
            throw new NoSuchElementException(errorMsg);
        }
        logger.info("Updated pet with ID: {}", id);
        return pet;
    }

    @Override
    public Pet getPetById(int petId) {
        return Optional.ofNullable(petStore.get(petId))
                .orElseThrow(() -> {
                    var errorMsg = "Pet with ID " + petId + " not found";
                    logger.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });
    }

    @Override
    public void deletePet(int petId) {
        if (!petStore.containsKey(petId)) {
            var errorMsg = "Pet with ID " + petId + " not found";
            logger.error(errorMsg);
            throw new NoSuchElementException(errorMsg);
        }
        petStore.remove(petId);
    }

    @Override
    public List<Pet> findPetsByStatus(String status) {
        return petStore.values().stream()
                .filter(pet -> status.contains(pet.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pet> findPetsByTags(List<String> tags) {
        return petStore.values().stream()
                .filter(pet -> pet.getTags() != null && pet.getTags().stream()
                        .anyMatch(tag -> tags.contains(tag.getName())))
                .collect(Collectors.toList());
    }
}
