package org.ddd.category.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;
import org.ddd.category.application.CategoryApplicationTestConfig;
import org.ddd.category.application.exceptions.CategoryNotFoundException;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.model.CategoryMother;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.ddd.shared.domain.valueobject.CategoryIdMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = CategoryApplicationTestConfig.class)
class CategoryNumOfProductsChangerTest {

  @Autowired
  @Qualifier("categoryNumOfProductsChangerTest")
  private CategoryNumOfProductsChanger categoryNumOfProductsChanger;

  @Autowired
  @Qualifier("categoryRepositoryTest")
  private CategoryRepository categoryRepository;

  @Test
  void testCategoryNumOfProductsChangerShouldChangeNumOfProducts() {
    // Given
    Category category = CategoryMother.random();
    Integer expectedNumOfProducts = 10;
    when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
    assertNotEquals(expectedNumOfProducts, category.getNumOfProducts());
    // When
    categoryNumOfProductsChanger.changeNumOfProducts(
        category.getId().value(), expectedNumOfProducts);
    // Then
    assertEquals(expectedNumOfProducts, category.getNumOfProducts());
    verify(categoryRepository, times(1)).findById(category.getId());
    verify(categoryRepository, times(1)).save(any(Category.class));
  }

  @Test
  void testCategoryNumOfProductsChangerShouldFailWithNonExistentCategory() {
    // Given
    CategoryId categoryId = CategoryIdMother.random();
    Integer expectedNumOfProducts = 10;
    UUID categoryIdValue = categoryId.value();
    when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
    // When
    Exception exception =
        assertThrows(
            CategoryNotFoundException.class,
            () ->
                categoryNumOfProductsChanger.changeNumOfProducts(
                    categoryIdValue, expectedNumOfProducts));
    // Then
    verify(categoryRepository, times(1)).findById(categoryId);
    verify(categoryRepository, never()).save(any(Category.class));
    assertEquals(
        String.format("Category with id %s not found", categoryIdValue), exception.getMessage());
  }

  @Test
  void testCategoryShouldThrowExceptionWhenNumOfProductsIsNegative() {
    // Given
    Category category = CategoryMother.random();
    Integer expectedNumOfProducts = -1;
    when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
    UUID categoryIdValue = category.getId().value();
    // When
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                categoryNumOfProductsChanger.changeNumOfProducts(
                    categoryIdValue, expectedNumOfProducts));
    // Then
    verify(categoryRepository, times(1)).findById(category.getId());
    verify(categoryRepository, never()).save(any(Category.class));
    assertEquals("Number of products cannot be negative", exception.getMessage());
  }
}
