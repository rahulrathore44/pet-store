package com.learning.builders;

import com.learning.dto.Category;

public class CategoryBuilder {
    private int id;
    private String name;

    public CategoryBuilder id(int id) {
        this.id = id;
        return this;
    }

    public CategoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    public static CategoryBuilder create() {
        return new CategoryBuilder();
    }
}
