package com.learning.annotations;

import com.learning.exceptions.ErrorResponse;
import io.micronaut.http.annotation.Header;
import io.micronaut.openapi.visitor.security.SecurityRule;
import io.micronaut.security.annotation.Secured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Operation(
        summary = "Delete a pet by ID",
        description = "Deletes the pet with the specified ID. Requires basic authentication.",
        security = @SecurityRequirement(name = "basicAuth")
)
@ApiResponse(responseCode = "200", description = "Pet deleted successfully")
@ApiResponse(responseCode = "401", description = "Unauthorized - Basic authentication required")
@ApiResponse(responseCode = "404", description = "Pet not found")
@ApiResponse(
        responseCode = "500",
        description = "Unexpected error",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@Parameter(
        name = "Authorization",
        description = "Basic authentication token. Format: 'Basic &lt;base64-encoded-credentials&gt;'",
        required = true,
        allowEmptyValue = false,
        in = ParameterIn.HEADER
)
@Parameter(
        name = "petId", // Change this to your desired name
        description = "ID of the pet to update",
        required = true,
        allowEmptyValue = false,
        schema = @Schema(type = "integer", format = "int32")
)
@Secured(SecurityRule.IS_AUTHENTICATED)
public @interface DeletePetById {
}
