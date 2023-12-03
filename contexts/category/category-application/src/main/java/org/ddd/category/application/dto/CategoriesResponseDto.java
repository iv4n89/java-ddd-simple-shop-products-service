package org.ddd.category.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ddd.category.domain.model.Category;

import java.util.List;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class CategoriesResponseDto {

  private final List<CategoryResponseDto> responses;

  public static CategoriesResponseDto fromCategories(List<Category> categories) {
    List<CategoryResponseDto> responses =
        categories.stream()
            .map(
                category ->
                    CategoryResponseDto.builder()
                        .id(category.getId().value().toString())
                        .name(category.getName().value())
                        .slug(category.getSlug().value())
                        .numberOfProducts(category.getNumOfProducts())
                        .isActive(category.isActive())
                        .build())
            .toList();

    return new CategoriesResponseDto(responses);
  }
}
