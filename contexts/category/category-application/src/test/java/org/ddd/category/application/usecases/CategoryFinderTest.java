package org.ddd.category.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ddd.category.application.CategoryApplicationTestConfig;
import org.ddd.category.application.exceptions.CategoryNotFoundException;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.model.CategoryMother;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategoryNameMother;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.category.domain.valueobject.CategorySlugMother;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.ddd.shared.domain.valueobject.CategoryIdMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = CategoryApplicationTestConfig.class)
class CategoryFinderTest {

  @Autowired
  @Qualifier("categoryFinderTest")
  private CategoryFinder categoryFinder;

  @Autowired
  @Qualifier("categoryRepositoryTest")
  private CategoryRepository categoryRepository;

  @Test
  void categoryFinderShouldFindAllCategories() {
    // Given
    List<Category> expected = List.of(CategoryMother.random(), CategoryMother.random());
    when(categoryRepository.findAll()).thenReturn(expected);
    // When
    List<Category> actual = categoryFinder.find();
    // Then
    verify(categoryRepository, times(1)).findAll();
    assertFalse(actual.isEmpty());
    assertEquals(expected.size(), actual.size());
    assertEquals(expected, actual);
  }

  @Test
  void categoryFinderShouldFindAllCategoriesEmptyList() {
    // Given
    List<Category> expected = List.of();
    when(categoryRepository.findAll()).thenReturn(expected);
    // When
    List<Category> actual = categoryFinder.find();
    // Then
    verify(categoryRepository, times(1)).findAll();
    assertTrue(actual.isEmpty());
  }

  @Test
  void testCategoryFinderShouldFindAnExistingCategoryById() {
    // Given
    Category expected = CategoryMother.random();
    CategoryId expectedId = expected.getId();
    when(categoryRepository.findById(any())).thenReturn(Optional.of(expected));
    // When
    Category actual = categoryFinder.find(expected.getId().value());
    // Then
    verify(categoryRepository, times(1)).findById(any());
    assertEquals(expected, actual);
    assertEquals(expectedId, actual.getId());
  }

  @Test
  void testCategoryFinderShouldFindAnExistingCategoryByName() {
    // Given
    Category expected = CategoryMother.random();
    CategoryName expectedName = expected.getName();
    when(categoryRepository.findByName(any())).thenReturn(Optional.of(expected));
    // When
    Category actual = categoryFinder.find(expected.getName().value());
    // Then
    verify(categoryRepository, times(1)).findByName(any());
    assertEquals(expected, actual);
    assertEquals(expectedName, actual.getName());
  }

  @Test
  void testCategoryFinderShouldFindAnExistingCategoryBySlug() {
    // Given
    Category expected = CategoryMother.random();
    CategoryName expectedName = expected.getName();
    when(categoryRepository.findBySlug(any())).thenReturn(Optional.of(expected));
    // When
    Category actual = categoryFinder.findBySlug(expected.getSlug().value());
    // Then
    verify(categoryRepository, times(1)).findBySlug(any());
    assertEquals(expected, actual);
    assertEquals(expectedName, actual.getName());
  }

  @Test
  void testCategoryFinderShouldThrowAnExceptionWhenCategoryByIdNotFound() {
    // Given
    CategoryId expectedId = CategoryIdMother.random();
    UUID expectedUUID = expectedId.value();
    when(categoryRepository.findById(any())).thenReturn(Optional.empty());
    // When
    Exception exception =
        assertThrows(
            CategoryNotFoundException.class,
            () -> categoryFinder.find(expectedUUID));
    // Then
    assertEquals("Category with id " + expectedUUID + " not found", exception.getMessage());
    verify(categoryRepository, times(1)).findById(any());
  }

  @Test
  void testCategoryFinderShouldThrowAnExceptionWhenCategoryByNameNotFound() {
    // Given
    CategoryName expectedName = CategoryNameMother.random();
    String expectedString = expectedName.value();
    when(categoryRepository.findByName(any())).thenReturn(Optional.empty());
    // When
    Exception exception =
        assertThrows(
            CategoryNotFoundException.class,
            () -> categoryFinder.find(expectedString));
    // Then
    assertEquals("Category with name " + expectedString + " not found", exception.getMessage());
    verify(categoryRepository, times(1)).findByName(any());
  }

  @Test
  void testCategoryFinderShouldThrowAnExceptionWhenCategoryBySlugNotFound() {
    // Given
    CategorySlug expectedName = CategorySlugMother.random();
    String expectedString = expectedName.value();
    when(categoryRepository.findBySlug(any())).thenReturn(Optional.empty());
    // When
    Exception exception =
        assertThrows(
            CategoryNotFoundException.class,
            () -> categoryFinder.findBySlug(expectedString));
    // Then
    assertEquals("Category with slug " + expectedString + " not found", exception.getMessage());
    verify(categoryRepository, times(1)).findBySlug(any());
  }
}
