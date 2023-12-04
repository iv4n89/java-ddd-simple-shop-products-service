package org.ddd.category.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.model.CategoryMother;
import org.ddd.category.infrastructure.CategoryInfrastructureTestConfiguration;
import org.ddd.category.infrastructure.persistence.mapper.CategoryDataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(classes = CategoryInfrastructureTestConfiguration.class)
@EmbeddedKafka(
    partitions = 1,
    topics = {"category-created-event"})
@TestPropertySource(
    properties = {
      "kafka-config.bootstrap-servers=${spring.embedded.kafka.brokers}",
      "kafka-config.category-created-topic=category-created-topic"
    })
class PostgresCategoryRepositoryTest {

  @Autowired
  @Qualifier("categoryJpaRepositoryTest")
  private CategoryJpaRepository categoryJpaRepositoryTest;

  @Autowired
  @Qualifier("categoryRepositoryInfraTest")
  private PostgresCategoryRepository postgresCategoryRepositoryTest;

  @Test
  void testFindAll() {
    // Given
    List<Category> expected = List.of(CategoryMother.random(), CategoryMother.random());
    when(categoryJpaRepositoryTest.findAll())
        .thenReturn(expected.stream().map(CategoryDataMapper::categoryToCategoryEntity).toList());

    // When
    List<Category> actual = postgresCategoryRepositoryTest.findAll();

    // Then
    verify(categoryJpaRepositoryTest, times(1)).findAll();
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertEquals(expected.get(0).getId(), actual.get(0).getId());
    assertEquals(expected.size(), actual.size());
  }

  @Test
  void testFindAllEmptyList() {
    // Given
    List<Category> expected = Collections.emptyList();
    when(categoryJpaRepositoryTest.findAll()).thenReturn(Collections.emptyList());

    // When
    List<Category> actual = postgresCategoryRepositoryTest.findAll();

    // Then
    verify(categoryJpaRepositoryTest, times(1)).findAll();
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertEquals(expected, actual);
  }

  @Test
  void testFindAnExistingCategoryById() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.findById(expected.getId().value()))
        .thenReturn(
            java.util.Optional.ofNullable(CategoryDataMapper.categoryToCategoryEntity(expected)));

    // When
    Category actual = postgresCategoryRepositoryTest.findById(expected.getId()).orElseThrow();

    // Then
    verify(categoryJpaRepositoryTest, times(1)).findById(expected.getId().value());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertEquals(expected.getId(), actual.getId());
  }

  @Test
  void testFindANonExistingCategoryById() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.findById(expected.getId().value()))
        .thenReturn(java.util.Optional.empty());

    // When
    Optional<Category> actual = postgresCategoryRepositoryTest.findById(expected.getId());

    // Then
    verify(categoryJpaRepositoryTest, times(1)).findById(expected.getId().value());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertTrue(actual.isEmpty());
  }

  @Test
  void testFindAnExistingCategoryByName() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.findByName(expected.getName().value()))
        .thenReturn(Optional.ofNullable(CategoryDataMapper.categoryToCategoryEntity(expected)));

    // When
    Category actual = postgresCategoryRepositoryTest.findByName(expected.getName()).orElseThrow();

    // Then
    verify(categoryJpaRepositoryTest, times(1)).findByName(expected.getName().value());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertEquals(expected.getId().value(), actual.getId().value());
    assertEquals(expected.getName(), actual.getName());
  }

  @Test
  void testFindANonExistingCategoryByName() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.findByName(expected.getName().value()))
        .thenReturn(Optional.empty());

    // When
    Optional<Category> actual = postgresCategoryRepositoryTest.findByName(expected.getName());

    // Then
    verify(categoryJpaRepositoryTest, times(1)).findByName(expected.getName().value());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertTrue(actual.isEmpty());
  }

  @Test
  void testFindAnExistingCategoryBySlug() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.findBySlug(expected.getSlug().value()))
        .thenReturn(Optional.ofNullable(CategoryDataMapper.categoryToCategoryEntity(expected)));

    // When
    Category actual = postgresCategoryRepositoryTest.findBySlug(expected.getSlug()).orElseThrow();

    // Then
    verify(categoryJpaRepositoryTest, times(1)).findBySlug(expected.getSlug().value());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertEquals(expected.getId().value(), actual.getId().value());
    assertEquals(expected.getSlug(), actual.getSlug());
  }

  @Test
  void testANonExistingCategoryBySlug() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.findBySlug(expected.getSlug().value()))
        .thenReturn(Optional.empty());

    // When
    Optional<Category> actual = postgresCategoryRepositoryTest.findBySlug(expected.getSlug());

    // Then
    verify(categoryJpaRepositoryTest, times(1)).findBySlug(expected.getSlug().value());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertTrue(actual.isEmpty());
  }

  @Test
  void testSaveANewProduct() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.save(any()))
        .thenReturn(CategoryDataMapper.categoryToCategoryEntity(expected));

    // When
    Category actual = postgresCategoryRepositoryTest.save(expected);

    // Then
    verify(categoryJpaRepositoryTest, times(1)).save(any());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertEquals(expected.getId().value(), actual.getId().value());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getSlug(), actual.getSlug());
    assertEquals(expected.getNumOfProducts(), actual.getNumOfProducts());
    assertTrue(actual.isActive());
  }

  @Test
  void testSaveAnExistingCategory() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.save(any()))
        .thenReturn(CategoryDataMapper.categoryToCategoryEntity(expected));

    // When
    Category actual = postgresCategoryRepositoryTest.save(expected);

    // Then
    verify(categoryJpaRepositoryTest, times(1)).save(any());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertEquals(expected.getId().value(), actual.getId().value());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getSlug(), actual.getSlug());
    assertEquals(expected.getNumOfProducts(), actual.getNumOfProducts());
    assertTrue(actual.isActive());
  }

  @Test
  void testErrorAtSavingANonValidCategory() {
    // Given
    Category expected = CategoryMother.random();
    when(categoryJpaRepositoryTest.save(any()))
        .thenThrow(new RuntimeException("Value must not be null!"));

    // When
    RuntimeException illegalArgumentException =
        assertThrows(
            RuntimeException.class, () -> postgresCategoryRepositoryTest.save(expected));

    // Then
    verify(categoryJpaRepositoryTest, times(1)).save(any());
    verifyNoMoreInteractions(categoryJpaRepositoryTest);

    assertEquals("Value must not be null!", illegalArgumentException.getMessage());
  }
}
