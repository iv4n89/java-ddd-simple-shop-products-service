package org.ddd.category.application.usecases;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.category.application.exceptions.CategoryNotFoundException;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryFinder {
  private final CategoryRepository categoryRepository;

  public List<Category> find() {
    return categoryRepository.findAll();
  }

  public Category find(String name) {
    return categoryRepository
        .findByName(new CategoryName(name))
        .orElseThrow(
            () -> new CategoryNotFoundException("Category with name %s not found".formatted(name)));
  }

  public Category find(UUID id) {
    return categoryRepository
        .findById(new CategoryId(id))
        .orElseThrow(
            () ->
                new CategoryNotFoundException(
                    "Category with id %s not found".formatted(id.toString())));
  }

  public Category findBySlug(String slug) {
    return categoryRepository
        .findBySlug(new CategorySlug(slug))
        .orElseThrow(
            () -> new CategoryNotFoundException("Category with slug %s not found".formatted(slug)));
  }
}
