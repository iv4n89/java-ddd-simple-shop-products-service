package org.ddd.category.application.usecases;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.category.application.exceptions.CategoryNotFoundException;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryRenamer {
  private final CategoryRepository categoryRepository;

  public void rename(UUID id, String name) {
    categoryRepository
        .findById(new CategoryId(id))
        .ifPresentOrElse(
            category -> {
              category.rename(name);
              categoryRepository.save(category);
            },
            () -> {
              throw new CategoryNotFoundException("Category with id " + id + " not found");
            });
  }
}
