package com.learning.controller;

import com.learning.builders.CategoryBuilder;
import com.learning.builders.PetBuilder;
import com.learning.builders.TagBuilder;
import com.learning.dto.Pet;
import com.learning.service.PetService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.xmlunit.assertj.XmlAssert.assertThat;

@MicronautTest
public class TestGetAllEndpoint {

    private static final String BASE_URL = "/pet";

    @MockBean(PetService.class)
    PetService petService() {
        return mock(PetService.class);
    }

    @Inject
    PetService petService;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testGetAllPetsReturnsOkWithPets_JSON() {
        var expectedResponse = """
                {
                  "pets" : [ {
                    "id" : 1,
                    "name" : "Buddy",
                    "category" : {
                      "id" : 1,
                      "name" : "Dog Category"
                    },
                    "photoUrls" : [ "http://example.com/photo1.jpg" ],
                    "tags" : [ {
                      "id" : 1,
                      "name" : "Dog"
                    } ],
                    "status" : "available"
                  }, {
                    "id" : 1,
                    "name" : "Buddy",
                    "category" : {
                      "id" : 1,
                      "name" : "Dog Category"
                    },
                    "photoUrls" : [ "http://example.com/photo1.jpg" ],
                    "tags" : [ {
                      "id" : 1,
                      "name" : "Dog"
                    } ],
                    "status" : "available"
                  } ]
                }
                """.trim();

        var tag1 = TagBuilder.create().id(1).name("Dog").build();
        var category1 = CategoryBuilder.create().id(1).name("Dog Category").build();
        var pet1 = PetBuilder.create().id(1).name("Buddy")
                .category(category1)
                .photoUrls(List.of("http://example.com/photo1.jpg"))
                .tags(List.of(tag1))
                .status("available")
                .build();

        var tag2 = TagBuilder.create().id(1).name("Dog").build();
        var category2 = CategoryBuilder.create().id(1).name("Dog Category").build();
        var pet2 = PetBuilder.create().id(1).name("Buddy")
                .category(category2)
                .photoUrls(List.of("http://example.com/photo1.jpg"))
                .tags(List.of(tag2))
                .status("available")
                .build();

        List<Pet> mockPets = List.of(pet1, pet2);
        when(petService.getAllPets()).thenReturn(mockPets);

        HttpResponse<String> response = client.toBlocking().exchange(HttpRequest.GET(BASE_URL + "/all"), String.class);

        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        assertThatJson(response.body())
                .isEqualTo(expectedResponse);
    }

    @Test
    void testGetAllPetsReturnsNoContent_JSON() {
        when(petService.getAllPets()).thenReturn(Collections.emptyList());

        HttpResponse<String> response = client.toBlocking().exchange(HttpRequest.GET(BASE_URL + "/all"), String.class);

        assertEquals(204, response.getStatus().getCode());
        assertNull(response.body());
    }

    @Test
    void testGetAllPetsReturnsOkWithPets_XML() {
        var expectedResponse = "<pets><pet><id>1</id><name>Buddy</name><category><id>1</id><name>Dog Category</name></category><status>available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>Dog</name></tags></tags></pet><pet><id>1</id><name>Buddy</name><category><id>1</id><name>Dog Category</name></category><status>available</status><photoUrls><photoUrls>http://example.com/photo1.jpg</photoUrls></photoUrls><tags><tags><id>1</id><name>Dog</name></tags></tags></pet></pets>";

        var tag1 = TagBuilder.create().id(1).name("Dog").build();
        var category1 = CategoryBuilder.create().id(1).name("Dog Category").build();
        var pet1 = PetBuilder.create().id(1).name("Buddy")
                .category(category1)
                .photoUrls(List.of("http://example.com/photo1.jpg"))
                .tags(List.of(tag1))
                .status("available")
                .build();

        var tag2 = TagBuilder.create().id(1).name("Dog").build();
        var category2 = CategoryBuilder.create().id(1).name("Dog Category").build();
        var pet2 = PetBuilder.create().id(1).name("Buddy")
                .category(category2)
                .photoUrls(List.of("http://example.com/photo1.jpg"))
                .tags(List.of(tag2))
                .status("available")
                .build();

        List<Pet> mockPets = List.of(pet1, pet2);
        when(petService.getAllPets()).thenReturn(mockPets);
        var request = HttpRequest.GET(BASE_URL + "/all")
                .accept(MediaType.APPLICATION_XML);

        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        assertThat(response.body()).and(expectedResponse).areIdentical();

    }

    @Test
    void testGetAllPetsReturnsNoContent_XML() {
        when(petService.getAllPets()).thenReturn(Collections.emptyList());
        var request = HttpRequest.GET(BASE_URL + "/all")
                .accept(MediaType.APPLICATION_XML);

        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        assertEquals(204, response.getStatus().getCode());
        assertNull(response.body());
    }
}
