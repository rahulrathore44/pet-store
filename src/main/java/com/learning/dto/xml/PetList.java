package com.learning.dto.xml;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.learning.dto.Pet;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.annotation.Serdeable.Deserializable;
import io.micronaut.serde.annotation.Serdeable.Serializable;

import java.util.List;

@Serializable
@Deserializable
@Introspected
@JacksonXmlRootElement(localName = "pets")
public class PetList {

    @JacksonXmlProperty(localName = "pet")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Pet> pets;

    public PetList(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}