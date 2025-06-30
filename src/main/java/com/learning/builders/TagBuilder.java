package com.learning.builders;

import com.learning.dto.Tag;

public class TagBuilder {
    private int id;
    private String name;

    public TagBuilder id(int id) {
        this.id = id;
        return this;
    }

    public TagBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Tag build() {
        return new Tag(id, name);
    }

    public static TagBuilder create() {
        return new TagBuilder();
    }
}
