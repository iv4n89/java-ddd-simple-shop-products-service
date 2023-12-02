package org.ddd.category.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import org.ddd.category.domain.CategoryDomainTestConfig;
import org.ddd.shared.domain.valueobject.WordMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CategoryDomainTestConfig.class)
class CategoryNameTest {

  @Test
  void testCreateAValidCategoryName() {
    // Given
    String expectedName = "Category name";
    CategoryName expectedObject = CategoryNameMother.create(expectedName);

    // When
    CategoryName actualObject = new CategoryName(expectedName);

    // Then
    assertEquals(expectedObject, actualObject);
    assertEquals(expectedName, actualObject.value());
  }

  @Test
  void testCreateAnInvalidGreaterThanThirtyCharactersLongCategoryName() {
    // Given
    String expectedName = WordMother.random(50);

    // When
    Throwable exception =
        assertThrows(IllegalArgumentException.class, () -> new CategoryName(expectedName));

    // Then
    assertEquals("The name must be less than 30 characters long", exception.getMessage());
  }

  @Test
  void testCreateAnInvalidLessThanThreeCharactersLongCategoryName() {
    // Given
    String expectedName = WordMother.random(2);

    // When
    Throwable exception =
        assertThrows(IllegalArgumentException.class, () -> new CategoryName(expectedName));

    // Then
    assertEquals("The name must be at least 3 characters long", exception.getMessage());
  }
}
