package org.ddd.product.domain;

import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.valueobject.*;

public class ProductMother {

  public static Product from(
      ProductId id, ProductName name, CategoryId categoryId, Money price, boolean isActive) {
    return Product.builder()
        .id(id)
        .name(name)
        .categoryId(categoryId)
        .price(price)
        .isActive(isActive)
        .build();
  }

  public static Product randomActive() {
    return Product.builder()
        .id(ProductIdMother.random())
        .name(ProductNameMother.random())
        .categoryId(CategoryIdMother.random())
        .price(MoneyMother.random())
        .isActive(true)
        .build();
  }

  public static Product randomInactive() {
    return Product.builder()
        .id(ProductIdMother.random())
        .name(ProductNameMother.random())
        .categoryId(CategoryIdMother.random())
        .price(MoneyMother.random())
        .isActive(false)
        .build();
  }
}
