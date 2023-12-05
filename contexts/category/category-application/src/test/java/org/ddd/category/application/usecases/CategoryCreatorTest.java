package org.ddd.category.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.ddd.category.application.CategoryApplicationTestConfig;
import org.ddd.category.domain.events.CategoryCreatedEvent;
import org.ddd.category.domain.events.CategoryCreatedEventPublisher;
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
class CategoryCreatorTest {

  @Autowired
  @Qualifier("categoryCreatorTest")
  private CategoryCreator categoryCreator;

  @Autowired
  @Qualifier("categoryRepositoryTest")
  private CategoryRepository categoryRepository;

  @Autowired
  @Qualifier("categoryCreatedEventPublisherTest")
    private CategoryCreatedEventPublisher categoryCreatedEventPublisher;

  @Test
  void categoryCreatorShouldCreateACategory() {
    // Given
    CategoryId id = CategoryIdMother.random();
    CategoryName name = CategoryNameMother.random();
    CategorySlug slug = CategorySlugMother.random();
    Integer numOfProducts = 0;
    boolean isActive = true;
    Category expected = CategoryMother.create(id, name, slug, numOfProducts, isActive);
    CategoryCreatedEvent expectedEvent = new CategoryCreatedEvent(expected.getId().toString());
    when(categoryRepository.save(any(Category.class))).thenReturn(expected);
    // When
    Category actual =
        categoryCreator.save(
            id.value(), name.value(), slug.value(), isActive);
    // Then
    verify(categoryRepository, times(1)).save(any(Category.class));
    verify(categoryCreatedEventPublisher, times(1)).publish(expectedEvent);
    assertEquals(expected, actual);
  }

  @Test
    void categoryCreatorShouldNotCreateACategoryWithNullId() {
        // Given
        CategoryId id = null;
        CategoryName name = CategoryNameMother.random();
        CategorySlug slug = CategorySlugMother.random();
        Integer numOfProducts = 0;
        boolean isActive = true;
        // When
        Exception exception =
            assertThrows(
                IllegalArgumentException.class,
                () ->
                    categoryCreator.save(
                        null, name.value(), slug.value(), isActive));
        // Then
        assertEquals("The value cannot be null", exception.getMessage());
        verify(categoryRepository, times(0)).save(any(Category.class));
        verify(categoryCreatedEventPublisher, times(0)).publish(any(CategoryCreatedEvent.class));
    }
}
