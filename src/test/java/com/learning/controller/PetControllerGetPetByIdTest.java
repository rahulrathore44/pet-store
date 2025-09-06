package com.learning.controller;

import com.learning.builders.CategoryBuilder;
import com.learning.builders.PetBuilder;
import com.learning.builders.TagBuilder;
import com.learning.dto.Pet;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class PetControllerGetPetByIdTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testGetPetById_Found_JSON() {
        var petId = 100;
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(petId).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        client.toBlocking().exchange(HttpRequest.POST("/pet", pet));
        var request = HttpRequest.GET("/pet/" + petId).accept(MediaType.APPLICATION_JSON);
        // Act: Get the pet by ID
        HttpResponse<Pet> response = client.toBlocking().exchange(request, Pet.class);

        // Assert: 200 OK and correct body
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals(100, response.body().getId());
        assertEquals("doggie", response.body().getName());
    }

    @Test
    void testGetPetById_Found_XML() {
        var petId = 101;
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(petId).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        client.toBlocking().exchange(HttpRequest.POST("/pet", pet));
        var request = HttpRequest.GET("/pet/" + petId).accept(MediaType.APPLICATION_XML);
        // Act: Get the pet by ID
        HttpResponse<Pet> response = client.toBlocking().exchange(request, Pet.class);

        // Assert: 200 OK and correct body
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals(101, response.body().getId());
        assertEquals("doggie", response.body().getName());
    }

    @Test
    void testGetPetById_Found_JSON_String() {
        var expectedResponseBody = """
                {"id":102,"name":"doggie","category":{"id":1,"name":"dog category"},"photoUrls":["http://example.com/photo1.jpg"],"tags":[{"id":1,"name":"dog"}],"status":"available"}
                """.stripIndent().trim();
        var petId = 102;
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(petId).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        client.toBlocking().exchange(HttpRequest.POST("/pet", pet));
        var request = HttpRequest.GET("/pet/" + petId).accept(MediaType.APPLICATION_JSON);
        // Act: Get the pet by ID
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assert: 200 OK and correct body
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals(expectedResponseBody, response.body());
    }

    @Test
    void testGetPetById_Found_XML_Sting() {
        var expectedResponseBody = """
                <pet><id>103</id><name>doggie</name><category><id>1</id><name>dog category</name></category><status>available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>dog</name></tags></tags></pet>
                """.stripIndent().trim();
        var petId = 103;
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(petId).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        client.toBlocking().exchange(HttpRequest.POST("/pet", pet));
        var request = HttpRequest.GET("/pet/" + petId).accept(MediaType.APPLICATION_XML);
        // Act: Get the pet by ID
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assert: 200 OK and correct body
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals(expectedResponseBody, response.body());
    }

    @Test
    void testGetPetById_NotFound_JSON() {
        var response = Assertions.assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(
                    HttpRequest.GET("/pet/99999").accept(MediaType.APPLICATION_JSON), String.class);
        });

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals("{\"error\":\"Pet with ID 99999 not found\"}", response.getResponse().body());
    }

    @Test
    void testGetPetById_NotFound_XML() {
        var response = Assertions.assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(
                    HttpRequest.GET("/pet/99999").accept(MediaType.APPLICATION_XML), String.class);
        });

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals("<ErrorResponse><error>Pet with ID 99999 not found</error></ErrorResponse>", response.getResponse().body());
    }
}

