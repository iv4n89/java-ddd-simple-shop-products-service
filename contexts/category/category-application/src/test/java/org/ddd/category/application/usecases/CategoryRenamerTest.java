package org.ddd.category.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.ddd.category.application.CategoryApplicationTestConfig;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.model.CategoryMother;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategoryNameMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = CategoryApplicationTestConfig.class)
class CategoryRenamerTest {

    @Autowired
    @Qualifier("categoryRenamerTest")
    private CategoryRenamer categoryRenamer;

    @Autowired
    @Qualifier("categoryRepositoryTest")
    private CategoryRepository categoryRepository;

    @Test
    void testCategoryRenamerShouldRenameACategory() {
        // Given
        Category category = CategoryMother.random();
        CategoryName name = category.getName();
        CategoryName expectedName = CategoryNameMother.random();
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        // When
        categoryRenamer.rename(category.getId().value(), expectedName.value());
        // Then
        assertNotEquals(name, category.getName());
        assertEquals(expectedName, category.getName());
        verify(categoryRepository, times(1)).findById(category.getId());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

}
