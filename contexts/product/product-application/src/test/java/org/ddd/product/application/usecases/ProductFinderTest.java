package org.ddd.product.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.ddd.product.application.ProductApplicationTestConfig;
import org.ddd.product.application.exceptions.ProductNotFoundException;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.valueobject.ProductId;
import org.ddd.shared.domain.valueobject.ProductIdMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = ProductApplicationTestConfig.class)
class ProductFinderTest {

  @Qualifier("productRepositoryTest")
  @Autowired
  private ProductRepository productRepository;

  @Qualifier("productFinderTest")
  @Autowired
  private ProductFinder productFinder;

  @Test
  void productFinderShouldFindAllProducts() {
    // Given
    List<Product> expected = List.of(ProductMother.randomActive(), ProductMother.randomActive());
    when(productRepository.findAll()).thenReturn(expected);
    // When
    List<Product> actual = productFinder.find();
    // Then
    verify(productRepository, times(1)).findAll();
    assertFalse(actual.isEmpty());
    assertEquals(expected.size(), actual.size());
    assertEquals(expected, actual);
  }

  @Test
  void productFinderShouldFindAllProductsEmptyList() {
    // Given
    List<Product> expected = List.of();
    when(productRepository.findAll()).thenReturn(expected);
    // When
    List<Product> actual = productFinder.find();
    // Then
    verify(productRepository, times(1)).findAll();
    assertTrue(actual.isEmpty());
  }

  @Test
  void productFinderShouldFindAnExistingProductById() {
    // Given
    Product expected = ProductMother.randomActive();
    ProductId expectedId = expected.getId();
    when(productRepository.findById(expectedId)).thenReturn(Optional.of(expected));
    // When
    Product actual = productFinder.find(expectedId.value());
    // Then
    verify(productRepository, times(1)).findById(expectedId);
    assertEquals(expected, actual);
  }

  @Test
  void productFinderShouldFindAnExistingProductByName() {
    // Given
    Product expected = ProductMother.randomActive();
    String expectedName = expected.getName().value();
    when(productRepository.findByName(expected.getName()))
        .thenReturn(java.util.Optional.of(expected));
    // When
    Product actual = productFinder.find(expectedName);
    // Then
    verify(productRepository, times(1)).findByName(expected.getName());
    assertEquals(expected, actual);
  }

  @Test
  void productFinderShouldThrowAnExceptionWhenProductByIdNotFound() {
    // Given
    ProductId expectedId = ProductIdMother.random();
    UUID expectedUUID = expectedId.value();
    when(productRepository.findById(expectedId)).thenReturn(java.util.Optional.empty());
    // When
    Exception exception =
        assertThrows(ProductNotFoundException.class, () -> productFinder.find(expectedUUID));
    // Then
    assertEquals("Product with id " + expectedUUID + " not found", exception.getMessage());
    verify(productRepository, times(1)).findById(expectedId);
  }

  @Test
  void productFinderShouldThrowAnExceptionWhenProductByNameNotFound() {
    // Given
    String expectedName = "Product Name";
    when(productRepository.findByName(new ProductName(expectedName)))
        .thenReturn(java.util.Optional.empty());
    // When
    Exception exception =
        assertThrows(ProductNotFoundException.class, () -> productFinder.find(expectedName));
    // Then
    assertEquals("Product with name " + expectedName + " not found", exception.getMessage());
    verify(productRepository, times(1)).findByName(new ProductName(expectedName));
  }
}
