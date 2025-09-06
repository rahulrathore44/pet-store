package com.learning.annotations;

import com.learning.dto.Status;
import com.learning.dto.xml.PetList;
import com.learning.exceptions.ErrorResponse;
import io.micronaut.http.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Operation(
        summary = "Finds pets by status",
        description = "Return pets by status. Allowable Values are: 'available', 'pending', 'sold'."
)
@Parameter(
        name = "status",
        description = "status of the pet to retrieve",
        required = true,
        in = ParameterIn.QUERY,
        schema = @Schema(type = "object", implementation = Status.class , allowableValues = {"available", "pending", "sold"})
)
@ApiResponse(
        responseCode = "200",
        description = "Pet found",
        content = {
                @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PetList.class)),
                @Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = PetList.class))
        }
)
@ApiResponse(
        responseCode = "204",
        description = "No pets found"
)
@ApiResponse(
        responseCode = "400",
        description = "Invalid status value",
        content = {
                @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)),
                @Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = ErrorResponse.class)),
        }
)
public @interface FindPetByStatus {
}
