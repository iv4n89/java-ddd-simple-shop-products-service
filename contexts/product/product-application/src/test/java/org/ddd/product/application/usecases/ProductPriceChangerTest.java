package org.ddd.product.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.ddd.product.application.ProductApplicationTestConfig;
import org.ddd.product.application.exceptions.ProductNotFoundException;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.shared.domain.valueobject.Money;
import org.ddd.shared.domain.valueobject.MoneyMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = ProductApplicationTestConfig.class)
class ProductPriceChangerTest {

  @Autowired
  @Qualifier("productPriceChangerTest")
  private ProductPriceChanger productPriceChanger;

  @Autowired
  @Qualifier("productRepositoryTest")
  private ProductRepository productRepository;

  @Test
  void productPriceChangerShouldIncreasePrice() {
    // Given
    Product product = ProductMother.randomActive();
    Money newPrice = MoneyMother.random();
    Money expectedPrice = product.getPrice().add(newPrice);
    when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
    // When
    productPriceChanger.increasePrice(product.getId().value(), newPrice.value());
    // Then
    assertEquals(expectedPrice, product.getPrice());
    verify(productRepository, times(1)).findById(product.getId());
    verify(productRepository, times(1)).save(any(Product.class));
  }

  @Test
  void productPriceChangerShouldDecreasePrice() {
    // Given
    Product product = ProductMother.randomActive();
    Money newPrice = product.getPrice().subtract(BigDecimal.valueOf(1));
    Money expectedPrice = product.getPrice().subtract(newPrice);
    when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
    // When
    productPriceChanger.decreasePrice(product.getId().value(), newPrice.value());
    // Then
    assertEquals(expectedPrice, product.getPrice());
    verify(productRepository, times(1)).findById(product.getId());
    verify(productRepository, times(1)).save(any(Product.class));
  }

  @Test
  void ProductPriceChangerShouldThrowExceptionWhenProductNotFound() {
    // Given
    Product product = ProductMother.randomActive();
    Money newPrice = MoneyMother.random();
    when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
    // When
    // Then
    UUID id = product.getId().value();
    BigDecimal price = newPrice.value();
    assertThrows(
        ProductNotFoundException.class,
        () -> productPriceChanger.increasePrice(id, price));
    verify(productRepository, times(1)).findById(product.getId());
    verify(productRepository, times(0)).save(any(Product.class));
  }
}
