package com.learning.service;

import com.learning.dto.Pet;
import com.learning.dto.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PetServiceImplTest {

    private PetServiceImpl petService;

    @BeforeEach
    void setUp() {
        petService = new PetServiceImpl();
    }

    @Test
    void testAddPet_AssignsIdAndStoresPet() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Max");
        pet.setStatus("available");

        Pet result = petService.addPet(pet);

        assertNotNull(result);
        assertTrue(result.getId() > 0);
        assertEquals("Max", result.getName());
        assertEquals("available", result.getStatus());

        // Verify pet is stored and retrievable
        Pet storedPet = petService.getPetById(result.getId());
        assertEquals(result, storedPet);
    }

    @Test
    void testUpdatePet_Success() {
        Pet pet = new Pet();
        pet.setName("Bella");
        pet.setStatus("available");
        Pet addedPet = petService.addPet(pet);

        addedPet.setName("BellaUpdated");
        addedPet.setStatus("sold");
        Pet updatedPet = petService.updatePet(addedPet);

        assertEquals("BellaUpdated", updatedPet.getName());
        assertEquals("sold", updatedPet.getStatus());
        assertEquals(addedPet.getId(), updatedPet.getId());
    }

    @Test
    void testUpdatePet_PetNotFound() {
        Pet pet = new Pet();
        pet.setId(999); // Non-existent ID
        pet.setName("Ghost");
        pet.setStatus("sold");

        assertThrows(NoSuchElementException.class, () -> petService.updatePet(pet));
    }

    @Test
    void testUpdatePetById_Success() {
        Pet pet = new Pet();
        pet.setName("Charlie");
        pet.setStatus("available");
        Pet addedPet = petService.addPet(pet);

        Pet updatedPet = petService.updatePet(addedPet.getId(), "CharlieUpdated", "pending");

        assertEquals(addedPet.getId(), updatedPet.getId());
        assertEquals("CharlieUpdated", updatedPet.getName());
        assertEquals("pending", updatedPet.getStatus());
    }

    @Test
    void testUpdatePetById_PetNotFound() {
        assertThrows(NoSuchElementException.class, () ->
                petService.updatePet(999, "Ghost", "unknown")
        );
    }

    @Test
    void testGetPetById_Success() {
        Pet pet = new Pet();
        pet.setName("Rocky");
        pet.setStatus("available");
        Pet addedPet = petService.addPet(pet);

        Pet foundPet = petService.getPetById(addedPet.getId());

        assertNotNull(foundPet);
        assertEquals(addedPet.getId(), foundPet.getId());
        assertEquals("Rocky", foundPet.getName());
        assertEquals("available", foundPet.getStatus());
    }

    @Test
    void testGetPetById_NotFound() {
        assertThrows(NoSuchElementException.class, () -> petService.getPetById(999));
    }


    @Test
    void testDeletePet_Success() {
        Pet pet = new Pet();
        pet.setName("Luna");
        pet.setStatus("available");
        Pet addedPet = petService.addPet(pet);

        // Should not throw
        assertDoesNotThrow(() -> petService.deletePet(addedPet.getId()));

        // After deletion, should throw when trying to get the pet
        assertThrows(NoSuchElementException.class, () -> petService.getPetById(addedPet.getId()));
    }

    @Test
    void testDeletePet_NotFound() {
        assertThrows(NoSuchElementException.class, () -> petService.deletePet(999));
    }


    @Test
    void testFindPetsByTags_FindsMatchingPets() {
        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("Bella");
        pet1.setStatus("available");
        pet1.setTags(List.of(new Tag(1, "cute"), new Tag(2, "small")));
        petService.addPet(pet1);

        Pet pet2 = new Pet();
        pet2.setId(2);
        pet2.setName("Max");
        pet2.setStatus("available");
        pet2.setTags(List.of(new Tag(1, "big")));
        petService.addPet(pet2);

        // Should find "Bella" by tag "cute"
        List<Pet> cutePets = petService.findPetsByTags(List.of("cute"));
        assertEquals(1, cutePets.size());
        assertEquals("Bella", cutePets.getFirst().getName());

        // Should find both by tag "small" or "big"
        List<Pet> pets = petService.findPetsByTags(List.of("small", "big"));
        assertEquals(2, pets.size());
        List<String> names = pets.stream().map(Pet::getName).toList();
        assertTrue(names.contains("Bella"));
        assertTrue(names.contains("Max"));
    }

    @Test
    void testFindPetsByTags_NoMatchOrNoTags() {
        Pet pet = new Pet();
        pet.setName("Daisy");
        pet.setStatus("available");
        pet.setTags(List.of(new Tag(1, "playful")));
        petService.addPet(pet);

        // No match
        List<Pet> result = petService.findPetsByTags(List.of("lazy"));
        assertTrue(result.isEmpty());

        // Pet with null tags
        Pet pet2 = new Pet();
        pet2.setName("NoTag");
        pet2.setStatus("available");
        pet2.setTags(null);
        petService.addPet(pet2);

        List<Pet> result2 = petService.findPetsByTags(List.of("anything"));
        assertTrue(result2.isEmpty());
    }

    @Test
    void testGetAllPets_EmptyStore_ReturnsEmptyList() {
        List<Pet> pets = petService.getAllPets();
        assertNotNull(pets);
        assertTrue(pets.isEmpty());
    }

    @Test
    void testGetAllPets_WithPets_ReturnsAllPets() {
        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("Max");
        pet1.setStatus("available");

        Pet pet2 = new Pet();
        pet2.setId(2);
        pet2.setName("Bella");
        pet2.setStatus("sold");

        petService.addPet(pet1);
        petService.addPet(pet2);

        List<Pet> pets = petService.getAllPets();
        assertEquals(2, pets.size());
        assertTrue(pets.contains(pet1));
        assertTrue(pets.contains(pet2));
    }
}