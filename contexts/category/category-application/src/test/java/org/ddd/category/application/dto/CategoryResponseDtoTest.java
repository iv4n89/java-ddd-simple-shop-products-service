package org.ddd.category.application.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.ddd.category.application.CategoryApplicationTestConfig;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.model.CategoryMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CategoryApplicationTestConfig.class)
class CategoryResponseDtoTest {

  @Test
  void categoryResponseDtoShouldBeCreated() {
    // Given
    Category category = CategoryMother.random();
    // When
    CategoryResponseDto categoryResponseDto =
        CategoryResponseDto.builder()
            .id(category.getId().value().toString())
            .name(category.getName().value())
            .slug(category.getSlug().value())
            .numberOfProducts(category.getNumOfProducts())
            .isActive(category.isActive())
            .build();
    // Then
    assertNotNull(categoryResponseDto);
    assertEquals(category.getId().value().toString(), categoryResponseDto.getId());
    assertEquals(category.getName().value(), categoryResponseDto.getName());
    assertEquals(category.getSlug().value(), categoryResponseDto.getSlug());
    assertEquals(category.getNumOfProducts(), categoryResponseDto.getNumberOfProducts());
    assertEquals(category.isActive(), categoryResponseDto.isActive());
    assertTrue(categoryResponseDto.isActive());
  }
}
