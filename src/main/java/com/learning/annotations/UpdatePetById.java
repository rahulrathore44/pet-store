package com.learning.annotations;

import com.learning.dto.Pet;
import com.learning.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Operation(
        summary = "Update a pet by ID",
        description = "Updates the name and status of a pet with the given ID."
)
@ApiResponse(
        responseCode = "200",
        description = "Pet updated successfully",
        content = @Content(schema = @Schema(implementation = Pet.class))
)
@ApiResponse(
        responseCode = "404",
        description = "Pet not found",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
        responseCode = "500",
        description = "Unexpected error",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@RequestBody(
        description = "Pet object to Update",
        required = true,
        content = @Content(schema = @Schema(implementation = Pet.class))
)
@Parameter(
        name = "petId", // Change this to your desired name
        description = "ID of the pet to update",
        required = true,
        allowEmptyValue = false,
        schema = @Schema(type = "integer", format = "int32")
)
public @interface UpdatePetById {
}
