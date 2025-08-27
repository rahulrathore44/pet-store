package com.learning.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.learning.builders.CategoryBuilder;
import com.learning.builders.PetBuilder;
import com.learning.builders.TagBuilder;
import com.learning.dto.Format;
import com.learning.dto.xml.PetList;
import com.learning.exceptions.DuplicatePetIdException;
import com.learning.storage.PetStorage;
import io.micronaut.serde.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UploadPetImplTest {

    private UploadPetImpl uploadPet;

    @BeforeEach
    void setUp() {
        PetStorage storage = new PetStorage();
        PetService petService = new PetServiceImpl(storage);
        ObjectMapper objectMapper = ObjectMapper.getDefault();
        uploadPet = new UploadPetImpl(petService, objectMapper);
    }

    @Test
    void testUploadPetsJson() {
        String json = """
                [
                	{
                		"id": 1,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	},
                	{
                		"id": 2,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	}
                ]
                """.stripIndent().trim();
        List<Integer> ids = uploadPet.uploadPets(json, Format.JSON);
        assertEquals(List.of(1, 2), ids);
    }

    @Test
    void testUploadPetsXml() throws Exception {
        var category = new CategoryBuilder().id(1).name("Dog").build();
        var tag = new TagBuilder().id(1).name("Four legs").build();
        var petOne = new PetBuilder().id(3).name("Bruno")
                .category(category)
                .photoUrls(List.of("http://localhost:8909/pics/dog.jpg"))
                .tags(List.of(tag))
                .status("sold")
                .build();
        var petTwo = new PetBuilder().id(4).name("Bruno")
                .category(category)
                .photoUrls(List.of("http://localhost:8909/pics/dog.jpg"))
                .tags(List.of(tag))
                .status("sold")
                .build();

        PetList petList = new PetList();
        petList.setPets(List.of(petOne, petTwo));
        String xml = new XmlMapper().writeValueAsString(petList);
        List<Integer> ids = uploadPet.uploadPets(xml, Format.XML);
        assertEquals(List.of(3, 4), ids);
    }

    @Test
    void testUploadPetsByteArray() {
        String json = """
                [
                	{
                		"id": 5,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	},
                	{
                		"id": 6,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	}
                ]
                """.stripIndent().trim();
        List<Integer> ids = uploadPet.uploadPets(json.getBytes(), Format.JSON);
        assertEquals(List.of(5, 6), ids);
    }

    @Test
    void testUploadPetsByteArray_XML() throws JsonProcessingException {
        var category = new CategoryBuilder().id(1).name("Dog").build();
        var tag = new TagBuilder().id(1).name("Four legs").build();
        var petOne = new PetBuilder().id(5).name("Bruno")
                .category(category)
                .photoUrls(List.of("http://localhost:8909/pics/dog.jpg"))
                .tags(List.of(tag))
                .status("sold")
                .build();
        var petTwo = new PetBuilder().id(6).name("Bruno")
                .category(category)
                .photoUrls(List.of("http://localhost:8909/pics/dog.jpg"))
                .tags(List.of(tag))
                .status("sold")
                .build();

        PetList petList = new PetList();
        petList.setPets(List.of(petOne, petTwo));
        String xml = new XmlMapper().writeValueAsString(petList);
        List<Integer> ids = uploadPet.uploadPets(xml.getBytes(), Format.XML);
        assertEquals(List.of(5, 6), ids);
    }

    @Test
    void testUploadPetsByteArray_XML_DuplicatePetIdException() throws JsonProcessingException {
        var category = new CategoryBuilder().id(1).name("Dog").build();
        var tag = new TagBuilder().id(1).name("Four legs").build();
        var petOne = new PetBuilder().id(5).name("Bruno")
                .category(category)
                .photoUrls(List.of("http://localhost:8909/pics/dog.jpg"))
                .tags(List.of(tag))
                .status("sold")
                .build();
        var petTwo = new PetBuilder().id(5).name("Bruno")
                .category(category)
                .photoUrls(List.of("http://localhost:8909/pics/dog.jpg"))
                .tags(List.of(tag))
                .status("sold")
                .build();

        PetList petList = new PetList();
        petList.setPets(List.of(petOne, petTwo));
        String xml = new XmlMapper().writeValueAsString(petList);
        assertThrows(DuplicatePetIdException.class, () -> uploadPet.uploadPets(xml.getBytes(), Format.XML));
    }

    @Test
    void testUploadPetsJson_DuplicatePetIdException() {
        String json = """
                [
                	{
                		"id": 2,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	},
                	{
                		"id": 2,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	}
                ]
                """.stripIndent().trim();
        assertThrows(DuplicatePetIdException.class, () -> uploadPet.uploadPets(json, Format.JSON));
    }
}
