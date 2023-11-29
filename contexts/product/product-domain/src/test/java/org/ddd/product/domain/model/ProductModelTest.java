package org.ddd.product.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.UUID;
import org.ddd.product.domain.ProductDomainTestConfig;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.ProductNameMother;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.ddd.shared.domain.valueobject.CategoryIdMother;
import org.ddd.shared.domain.valueobject.Money;
import org.ddd.shared.domain.valueobject.MoneyMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ProductDomainTestConfig.class)
class ProductModelTest {

  @Test
  void productShouldHaveAName() {
    // Given
    Product product = ProductMother.randomActive();
    // Then
    assertNotNull(product.getName());
    assertFalse(product.getName().value().isEmpty());
    assertTrue(product.getName().value().length() > 3);
    assertTrue(product.getName().value().length() < 50);
  }

  @Test
  void productShouldHaveACategoryId() {
    // Given
    Product product = ProductMother.randomActive();
    // Then
    assertNotNull(product.getCategoryId());
  }

  @Test
  void productShouldHaveAPrice() {
    // Given
    Product product = ProductMother.randomActive();
    // Then
    assertNotNull(product.getPrice());
  }

  @Test
  void productShouldHaveAnActiveStatus() {
    // Given
    Product product = ProductMother.randomActive();
    // Then
    assertTrue(product.isActive());
  }

  @Test
  void productShouldHaveAnInactiveStatus() {
    // Given
    Product product = ProductMother.randomInactive();
    // Then
    assertFalse(product.isActive());
  }

  @Test
  void productShouldBeRenamed() {
    // Given
    Product product = ProductMother.randomActive();
    ProductName oldName = product.getName();
    // When
    ProductName newName = ProductNameMother.random();
    product.rename(newName);
    // Then
    assertEquals(newName, product.getName());
    assertNotEquals(product.getName(), oldName);
  }

  @Test
  void productShouldIncreaseThePrice() {
    // Given
    Product product = ProductMother.randomActive();
    Money oldPrice = product.getPrice();
    // When
    product.increasePrice(MoneyMother.random());
    // Then
    assertNotEquals(product.getPrice(), oldPrice);
    assertFalse(oldPrice.isGreaterThan(product.getPrice()));
  }

  @Test
  void productShouldDecreaseThePrice() {
    // Given
    Product product = ProductMother.randomActive();
    Money oldPrice = product.getPrice();
    // When
    product.decreasePrice(MoneyMother.half(product.getPrice()));
    // Then
    assertNotEquals(product.getPrice(), oldPrice);
    assertTrue(oldPrice.isGreaterThan(product.getPrice()));
  }

  @Test
  void productShouldChangeTheCategory() {
    // Given
    Product product = ProductMother.randomActive();
    CategoryId oldCategoryId = product.getCategoryId();
    // When
    CategoryId newCategoryId = CategoryIdMother.random();
    product.changeCategory(newCategoryId);
    // Then
    assertEquals(newCategoryId, product.getCategoryId());
    assertNotEquals(product.getCategoryId(), oldCategoryId);
  }

  @Test
  void productShouldBeActivated() {
    // Given
    Product product = ProductMother.randomInactive();
    // When
    product.activate();
    // Then
    assertTrue(product.isActive());
  }

  @Test
  void productShouldBeDeactivated() {
    // Given
    Product product = ProductMother.randomActive();
    // When
    product.deactivate();
    // Then
    assertFalse(product.isActive());
  }

  @Test
  void productShouldBeCreatedFromPrimitives() {
    // Given
    UUID id = UUID.randomUUID();
    String name = "Product name";
    UUID categoryId = UUID.randomUUID();
    String price = "100";
    boolean isActive = true;
    // When
    Product product =
        Product.fromPrimitives(id.toString(), name, categoryId.toString(), price, isActive);
    // Then
    assertEquals(id, product.getId().value());
    assertEquals(name, product.getName().value());
    assertEquals(categoryId, product.getCategoryId().value());
    assertEquals(price, product.getPrice().value().toPlainString());
    assertEquals(isActive, product.isActive());
  }

  @Test
  void productShouldBeMappedToPrimitives() {
    // Given
    Product product = ProductMother.randomActive();
    // When
    Map<String, Object> primitives = product.toPrimitives();
    // Then
    assertEquals(product.getId().value().toString(), primitives.get("id"));
    assertEquals(product.getName().value(), primitives.get("name"));
    assertEquals(product.getCategoryId().value().toString(), primitives.get("categoryId"));
    assertEquals(product.getPrice().value().toString(), primitives.get("price"));
    assertEquals(product.isActive(), primitives.get("isActive"));
  }

  @Test
  void productShouldBeEqual() {
    // Given
    Product product = ProductMother.randomActive();
    // When
    Product product2 =
        ProductMother.from(
            product.getId(),
            product.getName(),
            product.getCategoryId(),
            product.getPrice(),
            product.isActive());
    // Then
    assertEquals(product, product2);
  }

  @Test
  void productShouldNotBeEqual() {
    // Given
    Product product = ProductMother.randomActive();
    // When
    Product product2 = ProductMother.randomActive();
    // Then
    assertNotEquals(product, product2);
  }

  @Test
  void productShouldNotBeEqualWhenComparingPrimitives() {
    // Given
    Product product = ProductMother.randomActive();
    // When
    Product product2 =
        Product.fromPrimitives(
            product.getId().value().toString(),
            product.getName().value(),
            product.getCategoryId().value().toString(),
            product.getPrice().value().toString(),
            !product.isActive());
    // Then
    assertNotEquals(product, product2);
  }

  @Test
  void productShouldNotBeEqualWhenComparingPrimitivesAndToPrimitives() {
    // Given
    Product product = ProductMother.randomActive();
    // When
    Product product2 =
        Product.fromPrimitives(
            product.getId().value().toString(),
            product.getName().value(),
            product.getCategoryId().value().toString(),
            product.getPrice().value().toString(),
            !product.isActive());
    // Then
    assertNotEquals(product, product2);
    assertNotEquals(product.toPrimitives(), product2.toPrimitives());
  }
}
