package org.ddd.category.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ddd.category.domain.model.Category;
import org.ddd.shared.application.Response;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@Builder
public class CategoryResponseDto extends Response {

  private final String id;
  private final String name;
  private final String slug;
  private final Integer numberOfProducts;
  private final boolean isActive;

  public static CategoryResponseDto fromCategory(Category category) {
    return CategoryResponseDto.builder()
        .id(category.getId().value().toString())
        .name(category.getName().value())
        .slug(category.getSlug().value())
        .numberOfProducts(category.getNumOfProducts())
        .isActive(category.isActive())
        .build();
  }
}
