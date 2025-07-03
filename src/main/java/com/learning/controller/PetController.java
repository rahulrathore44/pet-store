package com.learning.controller;

import com.learning.dto.Pet;
import com.learning.dto.xml.PetList;
import com.learning.exceptions.ErrorResponse;
import com.learning.exceptions.InvalidPetIdException;
import com.learning.exceptions.InvalidPetStatusException;
import com.learning.service.PetService;
import com.learning.validator.ValidationContext;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/pet")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PetController {

    private final PetService petService;
    private final ValidationContext validationContext;

    @Inject
    public PetController(PetService petService, ValidationContext validationContext) {
        this.petService = petService;
        this.validationContext = validationContext;
    }

    @Get("/all")
    public HttpResponse<?> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return pets.isEmpty() ? HttpResponse.noContent() : HttpResponse.ok(new PetList(pets));
    }

    @Get("/{petId}")
    public Pet getPetById(int petId) {
        return petService.getPetById(petId);
    }

    @Get("/findPetsByStatus")
    public HttpResponse<?> findPetsByStatus(@QueryValue String status) {
        try {
            validationContext.getPetStatusValidator().validate(status);
            List<Pet> pets = petService.findPetsByStatus(status);
            return pets.isEmpty() ? HttpResponse.noContent() : HttpResponse.ok(new PetList(pets));
        } catch (InvalidPetStatusException ex) {
            return HttpResponse.badRequest(new ErrorResponse(ex.getMessage()));
        }
    }

    @Get("/findPetsByTags")
    public List<Pet> findPetsByTags(@QueryValue List<String> tags) {
        return petService.findPetsByTags(tags);
    }

    @Post
    public HttpResponse<?> addPet(@Body Pet pet) {
        try {
            validationContext.getPetIdValidator().validate(pet.getId());
            validationContext.getPetStatusValidator().validate(pet.getStatus());
            return HttpResponse.created(petService.addPet(pet));
        } catch (InvalidPetStatusException | InvalidPetIdException ex) {
            return HttpResponse.badRequest(new ErrorResponse(ex.getMessage()));
        }
    }

    @Put
    public Pet updatePet(@Body Pet pet) {
        return petService.updatePet(pet);
    }

    @Patch("/{petId}")
    public Pet updatePet(int petId, @Body Pet pet) {
        return petService.updatePet(petId, pet.getName(), pet.getStatus());
    }

    @Delete("/{petId}")
    public void deletePet(int petId) {
        petService.deletePet(petId);
    }

}
