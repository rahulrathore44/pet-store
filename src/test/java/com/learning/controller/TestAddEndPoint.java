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

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.xmlunit.assertj.XmlAssert.assertThat;

@MicronautTest
class TestAddEndPoint {

    private static final String BASE_URL = "/pet";

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testAddPet_JSON_to_JSON() {
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(1).name("doggie")
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
        assertNotNull(response.body());
        assertEquals("doggie", response.body().getName());
        assertEquals("available", response.body().getStatus());
    }

    @Test
    void testAddPet_JSON_to_XML() {
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(2).name("doggie")
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
        assertNotNull(response.body());
        assertEquals("doggie", response.body().getName());
        assertEquals("available", response.body().getStatus());
    }

    @Test
    void testAddPet_XML_to_JSON() {
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(3).name("doggie")
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
        assertNotNull(response.body());
        assertEquals("doggie", response.body().getName());
        assertEquals("available", response.body().getStatus());
    }

    @Test
    void testAddPet_XML_to_XML() {
        var tag = TagBuilder.create().id(1).name("dog").build();
        var category = CategoryBuilder.create().id(1).name("dog category").build();
        var pet = PetBuilder.create().id(4).name("doggie")
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
        assertNotNull(response.body());
        assertEquals("doggie", response.body().getName());
        assertEquals("available", response.body().getStatus());
    }

    @Test
    void testAddPet_JSON_to_JSON_String() {
        var expectedResponse = """
                    {
                      "id" : 5,
                      "name" : "doggie",
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
        assertNotNull(response.body());
        assertThatJson(response.body().toString())
                .isEqualTo(expectedResponse);
    }

    @Test
    void testAddPet_JSON_to_XML_String() {
        var expectedResponse = "<pet><id>6</id><name>doggie</name><category><id>1</id><name>dog category</name></category><status>available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>dog</name></tags></tags></pet>";
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
        assertNotNull(response.body());
        assertThat(response.body()).and(expectedResponse).areIdentical();
    }

    @Test
    void testAddPet_XML_to_JSON_String() {
        var expectedResponse = """
                    {
                      "id" : 7,
                      "name" : "doggie",
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
        assertNotNull(response.body());
        assertThatJson(response.body()).isEqualTo(expectedResponse);
    }

    @Test
    void testAddPet_XML_to_XML_String() {
        var expectedResponse = "<pet><id>8</id><name>doggie</name><category><id>1</id><name>dog category</name></category><status>available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>dog</name></tags></tags></pet>";
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
        assertNotNull(response.body());
        assertThat(response.body()).and(expectedResponse).areIdentical();
    }

    @Test
    void testAddPet_XML_to_XML_String_Invalid_Status() {
        var requestBody = "<pet><id>9</id><name>doggie</name><category><id>1</id><name>dog category</name></category><status>not-available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>dog</name></tags></tags></pet>";

        HttpRequest<?> request = HttpRequest.POST(BASE_URL, requestBody)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML);

        var response = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(request, String.class));

        assertEquals(400, response.getStatus().getCode());
    }

    @Test
    void testAddPet_XML_to_JSON_String_Invalid_Id() {
        var requestBody = "<pet><id>-1</id><name>doggie</name><category><id>1</id><name>dog category</name></category><status>available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>dog</name></tags></tags></pet>";

        HttpRequest<?> request = HttpRequest.POST(BASE_URL, requestBody)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_JSON);

        var response = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(request, String.class));

        assertEquals(400, response.getStatus().getCode());
    }
}
