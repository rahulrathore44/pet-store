package com.learning.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable.Deserializable;
import io.micronaut.serde.annotation.Serdeable.Serializable;

@Serializable
@Deserializable
@Introspected
public class Tag {

    private int id;

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag {" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
