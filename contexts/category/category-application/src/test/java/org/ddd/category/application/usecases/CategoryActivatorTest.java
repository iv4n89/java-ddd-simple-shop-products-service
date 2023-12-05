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
class CategoryActivatorTest {

    @Autowired
    @Qualifier("categoryActivatorTest")
    private CategoryActivator categoryActivator;

    @Autowired
    @Qualifier("categoryRepositoryTest")
    private CategoryRepository categoryRepository;

    @Test
    void testCategoryActivatorShouldActivateACategory() {
        // Given
        Category category = CategoryMother.randomInactive();
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        // When
        categoryActivator.activate(category.getId().value());
        // Then
        assertTrue(category.isActive());
        verify(categoryRepository, times(1)).findById(category.getId());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testCategoryActivatorShouldDeactivateACategory() {
        // Given
        Category category = CategoryMother.random();
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        // When
        categoryActivator.deactivate(category.getId().value());
        // Then
        assertFalse(category.isActive());
        verify(categoryRepository, times(1)).findById(category.getId());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testCategoryActivatorShouldFailWithNonExistentCategory() {
        // Given
        CategoryId id = CategoryIdMother.random();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        // When
        UUID expectedId = id.value();
        Exception exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryActivator.activate(expectedId));
        String expectedMessage = "Category with id " + expectedId + " not found";
        String actualMessage = exception.getMessage();
        // Then
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, never()).save(any(Category.class));
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
