package com.learning.builders;

import com.learning.dto.Category;
import com.learning.dto.Pet;
import com.learning.dto.Tag;

import java.util.List;

public class PetBuilder {
    private int id;
    private String name;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public PetBuilder id(int id) {
        this.id = id;
        return this;
    }

    public PetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PetBuilder category(Category category) {
        this.category = category;
        return this;
    }

    public PetBuilder photoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    public PetBuilder tags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public PetBuilder status(String status) {
        this.status = status;
        return this;
    }

    public Pet build() {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setCategory(category);
        pet.setPhotoUrls(photoUrls);
        pet.setTags(tags);
        pet.setStatus(status);
        return pet;
    }

    public static PetBuilder create() {
        return new PetBuilder();
    }
}
