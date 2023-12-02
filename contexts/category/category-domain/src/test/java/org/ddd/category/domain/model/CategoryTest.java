package org.ddd.category.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.ddd.category.domain.CategoryDomainTestConfig;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategoryNameMother;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.category.domain.valueobject.CategorySlugMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CategoryDomainTestConfig.class)
class CategoryTest {

  private Category category;

  @BeforeEach
  void setUp() {
    category = CategoryMother.random();
  }

  @Test
  void testCreateAValidCategory() {
    // Given
    Category expectedObject = category;

    // When
    Category actualObject =
        Category.builder()
            .id(category.getId())
            .name(category.getName())
            .slug(category.getSlug())
            .numOfProducts(category.getNumOfProducts())
            .isActive(category.isActive())
            .build();

    // Then
    assertEquals(expectedObject, actualObject);
    assertEquals(category.getId(), actualObject.getId());
    assertEquals(category.getName(), actualObject.getName());
    assertEquals(category.getSlug(), actualObject.getSlug());
    assertEquals(category.getNumOfProducts(), actualObject.getNumOfProducts());
    assertEquals(category.isActive(), actualObject.isActive());
    assertTrue(actualObject.isActive());
  }

  @Test
  void testCreateAValidCategoryWithIsActiveFalse() {
    // Given
    Category expectedObject = CategoryMother.randomInactive();

    // When
    Category actualObject =
        Category.builder()
            .id(expectedObject.getId())
            .name(expectedObject.getName())
            .slug(expectedObject.getSlug())
            .numOfProducts(expectedObject.getNumOfProducts())
            .isActive(expectedObject.isActive())
            .build();

    // Then
    assertEquals(expectedObject, actualObject);
    assertEquals(expectedObject.getId(), actualObject.getId());
    assertEquals(expectedObject.getName(), actualObject.getName());
    assertEquals(expectedObject.getSlug(), actualObject.getSlug());
    assertEquals(expectedObject.getNumOfProducts(), actualObject.getNumOfProducts());
    assertEquals(expectedObject.isActive(), actualObject.isActive());
    assertFalse(actualObject.isActive());
  }

  @Test
  void testCategoryShouldBeActivated() {
    // Given
    Category expectedObject = CategoryMother.randomInactive();

    assertFalse(expectedObject.isActive());

    // When
    expectedObject.activate();

    // Then
    assertTrue(expectedObject.isActive());
  }

  @Test
  void testCategoryShouldBeDeactivated() {
    // Given
    Category expectedObject = CategoryMother.random();

    assertTrue(expectedObject.isActive());

    // When
    expectedObject.deactivate();

    // Then
    assertFalse(expectedObject.isActive());
  }

  @Test
  void testCategoryShouldBeRenamed() {
    // Given
    Category expectedObject = category;
    CategoryName newName = CategoryNameMother.random();
    assertNotEquals(expectedObject.getName(), newName);

    // When
    Category actualObject = expectedObject.rename(newName.value());

    // Then
    assertEquals(actualObject.getName(), newName);
    assertEquals(actualObject.getId(), expectedObject.getId());
  }

  @Test
  void testCategoryShouldBeRenamedWithSameName() {
    // Given
    Category expectedObject = category;
    CategoryName newName = CategoryNameMother.create(expectedObject.getName().value());
    assertEquals(expectedObject.getName(), newName);

    // When
    Category actualObject = expectedObject.rename(newName.value());

    // Then
    assertEquals(actualObject.getName(), newName);
    assertEquals(actualObject.getId(), expectedObject.getId());
  }

  @Test
  void testCategoryNumOfProductShouldBeChanged() {
    // Given
    Category expectedObject = category;
    Integer newNumOfProducts = 10;
    assertNotEquals(expectedObject.getNumOfProducts(), newNumOfProducts);

    // When
    Category actualObject = expectedObject.changeNumOfProducts(newNumOfProducts);

    // Then
    assertEquals(actualObject.getNumOfProducts(), newNumOfProducts);
    assertEquals(actualObject.getId(), expectedObject.getId());
  }

  @Test
  void testCategorySlugShouldBeChanged() {
    // Given
    Category expectedObject = category;
    CategorySlug newSlug = CategorySlugMother.random();
    assertNotEquals(expectedObject.getSlug(), newSlug);

    // When
    Category actualObject = expectedObject.changeSlug(newSlug.value());

    // Then
    assertEquals(actualObject.getSlug(), newSlug);
    assertEquals(actualObject.getId(), expectedObject.getId());
  }

    @Test
    void testCategoryShouldBeCreatedFromPrimitives() {
        // Given
        Category expectedObject = category;
        String id = expectedObject.getId().value().toString();
        String name = expectedObject.getName().value();
        String slug = expectedObject.getSlug().value();
        Integer numOfProducts = expectedObject.getNumOfProducts();
        boolean isActive = expectedObject.isActive();

        // When
        Category actualObject = Category.fromPrimitives(id, name, slug, numOfProducts, isActive);

        // Then
        assertEquals(expectedObject, actualObject);
        assertEquals(expectedObject.getId(), actualObject.getId());
        assertEquals(expectedObject.getName(), actualObject.getName());
        assertEquals(expectedObject.getSlug(), actualObject.getSlug());
        assertEquals(expectedObject.getNumOfProducts(), actualObject.getNumOfProducts());
        assertEquals(expectedObject.isActive(), actualObject.isActive());
        assertTrue(actualObject.isActive());
    }

    @Test
    void testCategoryShouldBeMappedToPrimtives() {
        // Given
        Category expectedObject = category;
        Map<String, Object> expectedPrimitives = Map.of(
            "id", expectedObject.getId().value().toString(),
            "name", expectedObject.getName().value(),
            "slug", expectedObject.getSlug().value(),
            "numOfProducts", expectedObject.getNumOfProducts(),
            "isActive", expectedObject.isActive()
        );

        // When
        Map<String, Object> actualPrimitives = expectedObject.toPrimitives();

        // Then
        assertEquals(expectedPrimitives, actualPrimitives);
        assertEquals(expectedObject.getId().value().toString(), actualPrimitives.get("id"));
        assertEquals(expectedObject.getName().value(), actualPrimitives.get("name"));
        assertEquals(expectedObject.getSlug().value(), actualPrimitives.get("slug"));
        assertEquals(expectedObject.getNumOfProducts(), actualPrimitives.get("numOfProducts"));
        assertEquals(expectedObject.isActive(), actualPrimitives.get("isActive"));
        assertTrue((boolean) actualPrimitives.get("isActive"));
    }
}
