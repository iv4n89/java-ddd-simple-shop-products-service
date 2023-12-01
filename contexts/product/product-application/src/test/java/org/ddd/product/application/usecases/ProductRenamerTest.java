package org.ddd.product.application.usecases;

import org.ddd.product.application.ProductApplicationTestConfig;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.ProductNameMother;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.product.domain.valueobject.ProductName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = ProductApplicationTestConfig.class)
public class ProductRenamerTest {

  @Autowired
  @Qualifier("productRenamerTest")
  private ProductRenamer productRenamer;

  @Autowired
  @Qualifier("productRepositoryTest")
  private ProductRepository productRepository;

  @Test
  void productRenamerShouldRenameAProduct() {
    // Given
    Product product = ProductMother.randomActive();
    ProductName name = product.getName();
    ProductName newName = ProductNameMother.random();
    when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
    // When
    productRenamer.rename(product.getId().value(), newName.value());
    // Then
    assertNotEquals(name, product.getName());
    assertEquals(newName, product.getName());
    verify(productRepository, times(1)).findById(product.getId());
    verify(productRepository, times(1)).save(any(Product.class));
  }
}
