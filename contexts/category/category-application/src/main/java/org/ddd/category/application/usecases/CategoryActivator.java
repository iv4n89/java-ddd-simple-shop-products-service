package org.ddd.category.application.usecases;

import lombok.RequiredArgsConstructor;
import org.ddd.category.application.exceptions.CategoryNotFoundException;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryActivator {
  private final CategoryRepository categoryRepository;

  public void activate(UUID id) {
    categoryRepository
        .findById(new CategoryId(id))
        .ifPresentOrElse(
            category -> {
              category.activate();
              categoryRepository.save(category);
            },
            () -> {
              throw new CategoryNotFoundException("Category with id " + id + " not found");
            });
  }

  public void deactivate(UUID id) {
    categoryRepository
        .findById(new CategoryId(id))
        .ifPresentOrElse(
            category -> {
              category.deactivate();
              categoryRepository.save(category);
            },
            () -> {
              throw new CategoryNotFoundException("Category with id " + id + " not found");
            });
  }
}
