package com.learning.controller;


import com.learning.builders.CategoryBuilder;
import com.learning.builders.PetBuilder;
import com.learning.builders.TagBuilder;
import com.learning.dto.Pet;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Random;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.xmlunit.assertj.XmlAssert.assertThat;

@MicronautTest
class TestUpdateByPetIdEndPoint {

    private static final String BASE_URL = "/pet";
    private static final Random random = new Random();

    @Inject
    @Client("/")
    HttpClient client;


    @Test
    void testAddPet_JSON_to_JSON() {
        var petId = random.nextInt(1000) + 10;
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(petId).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<Pet> request = HttpRequest.POST(BASE_URL, pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);

        HttpResponse<Pet> response = client.toBlocking().exchange(request, Pet.class);

        assertEquals(201, response.getStatus().getCode());

        // Update pet
        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/" + petId, pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);
        HttpResponse<Pet> updateResponse = client.toBlocking().exchange(updateRequest, Pet.class);

        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertEquals("doggie-updated", updateResponse.body().getName());

    }

    @Test
    void testAddPet_JSON_to_XML() {
        var petId = random.nextInt(1000) + 10;
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(petId).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<Pet> request = HttpRequest.POST(BASE_URL, pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_XML);

        HttpResponse<Pet> response = client.toBlocking().exchange(request, Pet.class);

        assertEquals(201, response.getStatus().getCode());

        // Update pet
        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/" + petId, pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_XML);
        HttpResponse<Pet> updateResponse = client.toBlocking().exchange(updateRequest, Pet.class);

        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertEquals("doggie-updated", updateResponse.body().getName());
    }

    @Test
    void testAddPet_XML_to_JSON() {
        var petId = random.nextInt(1000) + 10;
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(petId).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<Pet> request = HttpRequest.POST(BASE_URL, pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_JSON);

        HttpResponse<Pet> response = client.toBlocking().exchange(request, Pet.class);

        assertEquals(201, response.getStatus().getCode());

        // Update pet
        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/" + petId, pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_JSON_TYPE);
        HttpResponse<Pet> updateResponse = client.toBlocking().exchange(updateRequest, Pet.class);

        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertEquals("doggie-updated", updateResponse.body().getName());

    }

    @Test
    void testAddPet_XML_to_XML() {
        var petId = random.nextInt(1000) + 10;
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(petId).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<Pet> request = HttpRequest.POST(BASE_URL, pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML);

        HttpResponse<Pet> response = client.toBlocking().exchange(request, Pet.class);

        assertEquals(201, response.getStatus().getCode());

        // Update pet
        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/" + petId, pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML);
        HttpResponse<Pet> updateResponse = client.toBlocking().exchange(updateRequest, Pet.class);

        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertEquals("doggie-updated", updateResponse.body().getName());
    }

    @Test
    void testAddPet_JSON_to_JSON_String() {
        var expectedResponse = """
                    {
                      "id" : 5,
                      "name" : "doggie-updated",
                      "category" : {
                        "id" : 1,
                        "name" : "dog category"
                      },
                      "photoUrls" : [ "http://example.com/photo1.jpg" ],
                      "tags" : [ {
                        "id" : 1,
                        "name" : "dog"
                      } ],
                      "status" : "available"
                    }
                """.trim();
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(5).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<Pet> request = HttpRequest.POST(BASE_URL, pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);

        HttpResponse<?> response = client.toBlocking().exchange(request, String.class);

        assertEquals(201, response.getStatus().getCode());

        // Update pet
        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/5", pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);
        HttpResponse<?> updateResponse = client.toBlocking().exchange(updateRequest, String.class);

        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertThatJson(updateResponse.body().toString())
                .isEqualTo(expectedResponse);
    }

    @Test
    void testAddPet_JSON_to_XML_String() {
        var expectedResponse = "<pet><id>6</id><name>doggie-updated</name><category><id>1</id><name>dog category</name></category><status>available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>dog</name></tags></tags></pet>";
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(6).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<Pet> request = HttpRequest.POST(BASE_URL, pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_XML);

        HttpResponse<?> response = client.toBlocking().exchange(request, String.class);

        assertEquals(201, response.getStatus().getCode());

        // Update pet
        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/6", pet)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_XML);
        HttpResponse<?> updateResponse = client.toBlocking().exchange(updateRequest, String.class);

        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertThat(updateResponse.body()).and(expectedResponse).areIdentical();
    }

    @Test
    void testAddPet_XML_to_JSON_String() {
        var expectedResponse = """
                    {
                      "id" : 7,
                      "name" : "doggie-updated",
                      "category" : {
                        "id" : 1,
                        "name" : "dog category"
                      },
                      "photoUrls" : [ "http://example.com/photo1.jpg" ],
                      "tags" : [ {
                        "id" : 1,
                        "name" : "dog"
                      } ],
                      "status" : "available"
                    }
                """.trim();
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(7).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<Pet> request = HttpRequest.POST(BASE_URL, pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_JSON);

        HttpResponse<?> response = client.toBlocking().exchange(request, String.class);

        assertEquals(201, response.getStatus().getCode());

        // Update pet
        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/7", pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_JSON);
        HttpResponse<?> updateResponse = client.toBlocking().exchange(updateRequest, String.class);

        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertThatJson(updateResponse.body()).isEqualTo(expectedResponse);
    }

    @Test
    void testAddPet_XML_to_XML_String() {
        var expectedResponse = "<pet><id>8</id><name>doggie-updated</name><category><id>1</id><name>dog category</name></category><status>available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>dog</name></tags></tags></pet>";
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(8).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<Pet> request = HttpRequest.POST(BASE_URL, pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML);

        HttpResponse<?> response = client.toBlocking().exchange(request, String.class);

        assertEquals(201, response.getStatus().getCode());

        // Update pet
        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/8", pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML);
        HttpResponse<?> updateResponse = client.toBlocking().exchange(updateRequest, String.class);

        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertThat(updateResponse.body()).and(expectedResponse).areIdentical();
    }

    @Test
    void testAddPet_XML_to_XML_String_NotFound_Status() {
        var requestBody = "<pet><id>9</id><name>doggie</name><category><id>1</id><name>dog category</name></category><status>sold</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>dog</name></tags></tags></pet>";
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(9).name("doggie")
                .category(category)
                .photoUrls(Collections.singletonList("http://example.com/photo1.jpg"))
                .tags(Collections.singletonList(tag))
                .status("available")
                .build();

        HttpRequest<?> request = HttpRequest.POST(BASE_URL, requestBody)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML);

        HttpResponse<?> response = client.toBlocking().exchange(request, String.class);

        assertEquals(201, response.getStatus().getCode());

        pet.setName("doggie-updated");
        HttpRequest<Pet> updateRequest = HttpRequest.PATCH(BASE_URL + "/99999", pet)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML);

        var updateResponse = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(updateRequest, String.class));

        assertEquals(404, updateResponse.getStatus().getCode());
    }

}
