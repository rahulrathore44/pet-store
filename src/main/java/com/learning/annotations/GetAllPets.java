package com.learning.annotations;

import com.learning.dto.xml.PetList;
import com.learning.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
public @interface GetAllPets {
}
