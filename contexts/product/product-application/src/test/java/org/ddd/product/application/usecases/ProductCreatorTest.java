package org.ddd.product.application.usecases;

import org.ddd.product.application.ProductApplicationTestConfig;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.ProductNameMother;
import org.ddd.product.domain.events.ProductCreatedEvent;
import org.ddd.product.domain.events.ProductCreatedEventPublisher;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = ProductApplicationTestConfig.class)
class ProductCreatorTest {

  @Autowired private ProductRepository productRepository;

  @Autowired
  @Qualifier("productCreatorTest")
  private ProductCreator productCreator;

    @Qualifier("productCreatedEventPublisherTest")
  @Autowired private ProductCreatedEventPublisher productCreatedEventPublisher;

  @Test
  void productCreatorShouldCreateAProduct() {
    // Given
    ProductId id = ProductIdMother.random();
    ProductName name = ProductNameMother.random();
    Money price = MoneyMother.random();
    boolean isActive = true;
    CategoryId categoryId = CategoryIdMother.random();
    Product expected = ProductMother.from(id, name, categoryId, price, isActive);
    ProductCreatedEvent expectedEvent = new ProductCreatedEvent(expected.getId().toString());
    when(productRepository.save(any(Product.class))).thenReturn(expected);
    // When
    Product actual =
        productCreator.save(
            id.value(), name.value(), categoryId.value(), price.value().toPlainString(), isActive);
    // Then
    verify(productRepository, times(1)).save(any(Product.class));
    verify(productCreatedEventPublisher, times(1)).publish(expectedEvent);
    assertEquals(expected, actual);
  }

  @Test
  void productCreatorShouldNotCreateAProductWithNullId() {
    // Given
    ProductId id = null;
    ProductName name = ProductNameMother.random();
    Money price = MoneyMother.random();
    boolean isActive = true;
    CategoryId categoryId = CategoryIdMother.random();
    // When
    assertThrows(
        NullPointerException.class,
        () ->
            productCreator.save(
                null,
                name.value(),
                categoryId.value(),
                price.value().toPlainString(),
                isActive));
    // Then
    verify(productRepository, times(0)).save(any(Product.class));
    verify(productCreatedEventPublisher, times(0)).publish(any(ProductCreatedEvent.class));
  }
}
