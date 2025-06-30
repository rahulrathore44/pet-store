package com.learning.uilts;

import com.learning.builders.CategoryBuilder;
import com.learning.builders.PetBuilder;
import com.learning.builders.TagBuilder;
import com.learning.dto.Category;
import com.learning.dto.Pet;
import com.learning.dto.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static Pet createPet(int id, String name, String categoryName, int categoryId, String[] photoUrls, String[] tagNames, int[] tagIds, String status) {
        Category category = new CategoryBuilder()
                .id(categoryId)
                .name(categoryName)
                .build();

        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < tagNames.length; i++) {
            tags.add(new TagBuilder()
                    .id(tagIds[i])
                    .name(tagNames[i])
                    .build());
        }

        return new PetBuilder()
                .id(id)
                .name(name)
                .category(category)
                .photoUrls(Arrays.asList(photoUrls))
                .tags(tags)
                .status(status)
                .build();
    }

}
