package org.ddd.category.presentation.controller;

import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.category.application.dto.CategoryResponseDto;
import org.ddd.category.application.usecases.CategoryCreator;
import org.ddd.category.domain.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/categories")
@RequiredArgsConstructor
@RestController
public class CategoryPostController {

  private final CategoryCreator categoryCreator;

  @PostMapping
  public ResponseEntity<CategoryResponseDto> create(@RequestBody Request request) {
    Category category = categoryCreator.save(request.id, request.name(), request.slug(), true);
    return ResponseEntity.created(URI.create("/categories"))
        .body(CategoryResponseDto.fromCategory(category));
  }

  private record Request(UUID id, String name, String slug) {}
}
