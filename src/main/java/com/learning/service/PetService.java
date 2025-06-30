package com.learning.service;

import com.learning.dto.Pet;

import java.util.List;

public interface PetService {

    /**
     * Retrieves all pets currently stored in the system.
     *
     * @return a {@link List} of {@link Pet} objects representing every pet in the system;
     * returns an empty list if no pets are found
     */
    List<Pet> getAllPets();

    /**
     * Adds a new pet to the system.
     *
     * @param pet the pet to add
     * @return the added pet
     */
    Pet addPet(Pet pet);

    /**
     * Retrieves a pet by its ID.
     *
     * @param id the ID of the pet
     * @return the pet with the specified ID, or null if not found
     */
    Pet getPetById(int id);

    /**
     * Updates an existing pet.
     *
     * @param pet the pet with updated information
     * @return the updated pet
     */
    Pet updatePet(Pet pet);


    /**
     * Updates the name and status of a pet identified by its ID.
     *
     * @param id     the ID of the pet to update
     * @param name   the new name for the pet
     * @param status the new status for the pet (e.g., "available", "pending", "sold")
     * @return the updated pet
     */
    Pet updatePet(int id, String name, String status);

    /**
     * Deletes a pet by its ID.
     *
     * @param id the ID of the pet to delete
     */
    void deletePet(int id);

    /**
     * Finds all pets that match the given status.
     *
     * @param status the status to filter pets by (e.g., "available", "pending", "sold")
     * @return a list of pets with the specified status
     */
    List<Pet> findPetsByStatus(String status);

    /**
     * Finds all pets that have any of the specified tags.
     *
     * @param tags the list of tag names to filter pets by
     * @return a list of pets that have at least one of the specified tags
     */
    List<Pet> findPetsByTags(List<String> tags);
}

