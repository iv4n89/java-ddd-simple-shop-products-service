package org.ddd.category.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import org.ddd.category.domain.CategoryDomainTestConfig;
import org.ddd.shared.domain.valueobject.WordMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CategoryDomainTestConfig.class)
class CategorySlugTest {

    @Test
    void testCreateAValidCategorySlug() {
        // Given
        String expectedSlug = WordMother.random(5);
        CategorySlug expectedObject = CategorySlugMother.create(expectedSlug);

        // When
        CategorySlug actualObject = new CategorySlug(expectedSlug);

        // Then
        assertEquals(expectedObject, actualObject);
        assertEquals(expectedSlug, actualObject.value());
    }

    @Test
    void testCreateAnInvalidMoreThanFiveCharactersLongCategorySlug() {
        // Given
        String expectedSlug = WordMother.random(10);

        // When
        Throwable exception =
            assertThrows(IllegalArgumentException.class, () -> new CategorySlug(expectedSlug));

        // Then
        assertEquals("The slug must be 5 characters long", exception.getMessage());
    }

    @Test
    void testCreateAnInvalidLessThanFiveCharactersLongCategorySlug() {
        // Given
        String expectedSlug = WordMother.random(4);

        // When
        Throwable exception =
            assertThrows(IllegalArgumentException.class, () -> new CategorySlug(expectedSlug));

        // Then
        assertEquals("The slug must be 5 characters long", exception.getMessage());
    }
}
