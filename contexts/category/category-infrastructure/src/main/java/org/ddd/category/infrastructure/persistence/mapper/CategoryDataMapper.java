package org.ddd.category.infrastructure.persistence.mapper;

import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.category.infrastructure.persistence.entity.CategoryEntity;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.springframework.stereotype.Component;

@Component
public class CategoryDataMapper {

  private CategoryDataMapper() {}

  public static Category categoryEntityToCategory(CategoryEntity categoryEntity) {
    return Category.builder()
        .id(new CategoryId(categoryEntity.getId()))
        .name(new CategoryName(categoryEntity.getName()))
        .slug(new CategorySlug(categoryEntity.getSlug()))
        .numOfProducts(categoryEntity.getNumOfProducts())
        .isActive(categoryEntity.isActive())
        .build();
  }

  public static CategoryEntity categoryToCategoryEntity(Category category) {
    return CategoryEntity.builder()
        .id(category.getId().value())
        .name(category.getName().value())
        .slug(category.getSlug().value())
        .numOfProducts(category.getNumOfProducts())
        .isActive(category.isActive())
        .build();
  }
}
