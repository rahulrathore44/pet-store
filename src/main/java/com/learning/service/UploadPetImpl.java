package com.learning.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.learning.dto.Format;
import com.learning.dto.Pet;
import com.learning.dto.xml.PetList;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class UploadPetImpl implements UploadPet {

    private final PetService petService;
    private static final XmlMapper XML_MAPPER = new XmlMapper();
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(UploadPetImpl.class);

    @Inject
    public UploadPetImpl(PetService petService, ObjectMapper objectMapper) {
        this.petService = petService;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Integer> uploadPets(byte[] data, Format format) {
        String content = readData(data);
        return uploadPets(content, format);
    }

    @Override
    public List<Integer> uploadPets(String data, Format format) {
        var pets = parsePets(data, format);
        var ids = pets.stream().map(Pet::getId).toList();
        pets.forEach(petService::addPet);
        return ids;
    }

    private String readData(byte[] data) {
        return new String(data);
    }

    private List<Pet> parsePets(String data, Format format) {
        try {
            Argument<List<Pet>> argument = Argument.listOf(Pet.class);
            return switch (format) {
                case JSON -> objectMapper.readValue(data, argument);
                case XML -> {
                    PetList pets = XML_MAPPER.readValue(data, PetList.class);
                    yield pets.getPets();
                }
                default -> throw new IllegalArgumentException("Unsupported format: " + format);
            };
        } catch (Exception e) {
            logger.error("Failed to parse pets ", e);
            throw new RuntimeException("Failed to parse pets ", e);
        }
    }

}
