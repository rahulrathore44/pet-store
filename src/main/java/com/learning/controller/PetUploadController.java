package com.learning.controller;

import com.learning.dto.Format;
import com.learning.dto.xml.PetIdList;
import com.learning.service.UploadPetImpl;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Part;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;

import java.io.IOException;

@Controller("/pet/upload")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PetUploadController {

    private final UploadPetImpl uploadPet;

    @Inject
    public PetUploadController(UploadPetImpl uploadPet) {
        this.uploadPet = uploadPet;
    }

    @Post
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public HttpResponse<?> uploadPetsFile(@Part CompletedFileUpload file,
                                          @QueryValue Format format) throws IOException {
        var ids = uploadPet.uploadPets(file.getBytes(), format);
        var filename = file.getFilename();
        return switch (format) {
            case JSON -> HttpResponse.ok(ids)
                    .header("Content-Type", MediaType.APPLICATION_JSON)
                    .header("X-File-Name", filename);
            case XML -> HttpResponse.ok(new PetIdList(ids))
                    .header("Content-Type", MediaType.APPLICATION_XML)
                    .header("X-File-Name", filename);
            case CSV, TXT -> null;
        };
    }

    @Post(value = "/raw", consumes = {MediaType.APPLICATION_OCTET_STREAM})
    public HttpResponse<?> uploadPetsBytes(@Body byte[] data,
                                           @QueryValue Format format) {
        var ids = uploadPet.uploadPets(data, format);
        return switch (format) {
            case JSON -> HttpResponse.ok(ids)
                    .header("Content-Type", MediaType.APPLICATION_JSON);
            case XML -> HttpResponse.ok(new PetIdList(ids))
                    .header("Content-Type", MediaType.APPLICATION_XML);
            case CSV, TXT -> null;
        };
    }
}

