package org.ddd.category.presentation.controller;

import org.ddd.category.domain.model.Category;

import java.util.Map;

public class CategoryToCreateRequestDto {
    public static Map<String, Object> categoryToCreateRequestDto(Category category) {
        return Map.of(
                "id", category.getId().value().toString(),
                "name", category.getName().value(),
                "slug", category.getSlug().value(),
                "active", category.isActive()
        );
    }
}
