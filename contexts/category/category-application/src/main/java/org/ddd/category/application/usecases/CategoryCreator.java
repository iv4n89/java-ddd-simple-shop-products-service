package org.ddd.category.application.usecases;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.category.domain.events.CategoryCreatedEvent;
import org.ddd.category.domain.events.CategoryCreatedEventPublisher;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryCreator {
  private final CategoryRepository categoryRepository;
  private final CategoryCreatedEventPublisher categoryCreatedEventPublisher;

  public Category save(UUID id, String name, String slug, boolean isActive) {
    boolean exists = categoryRepository.existsById(new CategoryId(id));
    Category category = Category.fromPrimitives(id.toString(), name, slug, 0, isActive);
    Category categorySaved = categoryRepository.save(category);
    if (!exists) {
      CategoryCreatedEvent categoryCreatedEvent =
          new CategoryCreatedEvent(categorySaved.getId().toString());
      categoryCreatedEventPublisher.publish(categoryCreatedEvent);
    }
    return categorySaved;
  }
}
