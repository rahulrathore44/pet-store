package com.learning.annotations;

import com.learning.dto.Pet;
import com.learning.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Operation(
        summary = "Get pet by ID",
        description = "Retrieves a pet by its unique ID. Returns 404 if not found."
)
@Parameter(
        name = "petId",
        description = "ID of the pet to retrieve",
        required = true,
        in = ParameterIn.PATH,
        schema = @Schema(type = "integer")
)
@ApiResponse(
        responseCode = "200",
        description = "Pet found",
        content = @Content(schema = @Schema(implementation = Pet.class))
)
@ApiResponse(
        responseCode = "404",
        description = "Pet not found",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface GetPetById {
}
