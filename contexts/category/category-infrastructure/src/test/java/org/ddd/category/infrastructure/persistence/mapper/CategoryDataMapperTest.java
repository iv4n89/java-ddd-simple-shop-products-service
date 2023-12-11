package org.ddd.category.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.model.CategoryMother;
import org.ddd.category.domain.valueobject.CategoryNameMother;
import org.ddd.category.domain.valueobject.CategorySlugMother;
import org.ddd.category.infrastructure.CategoryInfrastructureTestConfiguration;
import org.ddd.category.infrastructure.persistence.entity.CategoryEntity;
import org.ddd.shared.domain.valueobject.CategoryIdMother;
import org.ddd.shared.domain.valueobject.IntegerMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(classes = CategoryInfrastructureTestConfiguration.class)
@EmbeddedKafka(partitions = 1, topics = {"category-created-event"})
class CategoryDataMapperTest {

    @Test
    void testMapperShouldMapToEntity() {
        // Given
        Category category = CategoryMother.random();

        // When
        CategoryEntity categoryEntity = CategoryDataMapper.categoryToCategoryEntity(category);

        // Then
        assertEquals(category.getId().value(), categoryEntity.getId());
        assertEquals(category.getName().value(), categoryEntity.getName());
        assertEquals(category.getSlug().value(), categoryEntity.getSlug());
        assertEquals(category.getNumOfProducts(), categoryEntity.getNumOfProducts());
        assertTrue(category.isActive());
        assertTrue(categoryEntity.isActive());
    }

    @Test
    void testMapperShouldMapToDomain() {
        // Given
        CategoryEntity categoryEntity = CategoryEntity.builder()
            .id(CategoryIdMother.random().value())
            .name(CategoryNameMother.random().value())
            .slug(CategorySlugMother.random().value())
            .numOfProducts(IntegerMother.random())
            .isActive(true)
            .build();

        // When
        Category category = CategoryDataMapper.categoryEntityToCategory(categoryEntity);

        // Then
        assertEquals(categoryEntity.getId(), category.getId().value());
        assertEquals(categoryEntity.getName(), category.getName().value());
        assertEquals(categoryEntity.getSlug(), category.getSlug().value());
        assertEquals(categoryEntity.getNumOfProducts(), category.getNumOfProducts());
        assertTrue(category.isActive());
        assertTrue(categoryEntity.isActive());
    }

}
