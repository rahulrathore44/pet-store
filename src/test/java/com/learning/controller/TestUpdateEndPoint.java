package com.learning.controller;


import com.learning.dto.Pet;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class TestUpdateEndPoint {

    /*@Inject
    @Client("/")
    HttpClient client;

    @Test
    void testUpdatePet() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("doggie");
        pet.setStatus("available");

        HttpRequest<Pet> request = HttpRequest.PUT("/pet", pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE);
        HttpResponse<Pet> response = client.toBlocking().exchange(request, Pet.class);

        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals("doggie", response.body().getName());
        assertEquals("available", response.body().getStatus());
    }*/
}
