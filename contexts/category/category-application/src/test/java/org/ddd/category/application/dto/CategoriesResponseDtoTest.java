package org.ddd.category.application.dto;

import org.ddd.category.application.CategoryApplicationTestConfig;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.model.CategoryMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CategoryApplicationTestConfig.class)
class CategoriesResponseDtoTest {

  @Test
  void categoriesResponseDtoShouldBeCreated() {
    // Given
    List<Category> categories =
        List.of(CategoryMother.random(), CategoryMother.random(), CategoryMother.randomInactive());
    // When
    CategoriesResponseDto categoriesResponseDto = CategoriesResponseDto.fromCategories(categories);
    // Then
    assertNotNull(categoriesResponseDto);
    assertEquals(categories.size(), categoriesResponseDto.getResponses().size());
    assertEquals(
        categories.get(0).getId().value().toString(),
        categoriesResponseDto.getResponses().get(0).getId());
    assertEquals(
        categories.get(0).getName().value(), categoriesResponseDto.getResponses().get(0).getName());
    assertEquals(
        categories.get(0).getSlug().value(), categoriesResponseDto.getResponses().get(0).getSlug());
    assertEquals(
        categories.get(0).getNumOfProducts(),
        categoriesResponseDto.getResponses().get(0).getNumberOfProducts());
    assertEquals(
        categories.get(0).isActive(), categoriesResponseDto.getResponses().get(0).isActive());
    assertTrue(categoriesResponseDto.getResponses().get(0).isActive());
    assertFalse(categoriesResponseDto.getResponses().get(2).isActive());
  }
}
