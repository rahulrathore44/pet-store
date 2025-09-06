package com.learning.annotations;

import com.learning.dto.xml.PetIdList;
import com.learning.exceptions.ErrorResponse;
import io.micronaut.http.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Operation(
        summary = "Upload pets as raw bytes",
        description = "Uploads a list of pets using raw byte data. The format must be specified as a query parameter."
)
@RequestBody(
        description = "Raw pet data file",
        required = true,
        content = @Content(
                mediaType = MediaType.APPLICATION_OCTET_STREAM
        )
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "List of pet IDs",
                content = {
                        @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = List.class), examples = @ExampleObject(value = "[1,2,3]")),
                        @Content(mediaType = "application/xml", schema = @Schema(type = "array", implementation = PetIdList.class), examples = @ExampleObject(value = "<pets>\n" +
                                "    <id>1</id>\n" +
                                "    <id>2</id>\n" +
                                "</pets>"))
                }
        ),
        @ApiResponse(
                responseCode = "422",
                description = "Unprocessable Entity",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UploadPetsFromBinary {
}
