package com.learning.annotations;

import com.learning.dto.Pet;
import com.learning.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
public @interface AddPet {
}
