package com.learning.controller;

import com.learning.dto.Pet;
import com.learning.dto.xml.PetList;
import com.learning.exceptions.DuplicatePetIdException;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Get All Pets",
            description = "Retrieves a list of all pets. If no pets are found, returns a 204 No Content response."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of all pets",
            content = @Content(schema = @Schema(implementation = PetList.class))
    )
    @ApiResponse(
            responseCode = "204",
            description = "No pets found",
            content = @Content(schema = @Schema(implementation = Void.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Unexpected error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
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
    @Operation(
            summary = "Add a new pet",
            description = "Creates a new pet in the store"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Pet created",
            content = @Content(schema = @Schema(implementation = Pet.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid input",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "422",
            description = "Unprocessable Entity",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Unexpected error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @RequestBody(
            description = "Pet object to add",
            required = true,
            content = @Content(schema = @Schema(implementation = Pet.class))
    )
    public HttpResponse<?> addPet(@Body Pet pet) {
        try {
            validationContext.getPetIdValidator().validate(pet.getId());
            validationContext.getPetStatusValidator().validate(pet.getStatus());
            return HttpResponse.created(petService.addPet(pet));
        } catch (InvalidPetStatusException | InvalidPetIdException ex) {
            return HttpResponse.badRequest(new ErrorResponse(ex.getMessage()));
        } catch (DuplicatePetIdException ex) {
            return HttpResponse.unprocessableEntity().body(new ErrorResponse(ex.getMessage()));
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
