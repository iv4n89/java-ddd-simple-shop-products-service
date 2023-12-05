package org.ddd.category.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.ddd.category.application.dto.CategoriesResponseDto;
import org.ddd.category.application.dto.CategoryResponseDto;
import org.ddd.category.application.usecases.CategoryFinder;
import org.ddd.category.domain.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/categories")
@RequiredArgsConstructor
@RestController
public class CategoryGetController {

    private final CategoryFinder categoryFinder;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll() {
        List<Category> categories = categoryFinder.find();
        CategoriesResponseDto response = CategoriesResponseDto.fromCategories(categories);
        return ResponseEntity.ok(response.getResponses());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable UUID id) {
        Category category = categoryFinder.find(id);
        CategoryResponseDto response = CategoryResponseDto.fromCategory(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDto> findByName(@PathVariable String name) {
        Category category = categoryFinder.find(name);
        CategoryResponseDto response = CategoryResponseDto.fromCategory(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoryResponseDto> findBySlug(@PathVariable String slug) {
        Category category = categoryFinder.findBySlug(slug);
        CategoryResponseDto response = CategoryResponseDto.fromCategory(category);
        return ResponseEntity.ok(response);
    }

}
