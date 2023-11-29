package org.ddd.product.domain.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.AggregateRoot;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.ddd.shared.domain.valueobject.Money;
import org.ddd.shared.domain.valueobject.ProductId;

public class Product implements AggregateRoot {

  private final ProductId id;
  private CategoryId categoryId;
  private ProductName name;
  private Money price;
  private boolean isActive;

  private Product(Builder builder) {
    id = builder.id;
    name = builder.name;
    categoryId = builder.categoryId;
    price = builder.price;
    isActive = builder.isActive;
  }

  public void increasePrice(Money money) {
    price = price.add(money);
  }

  public void decreasePrice(Money money) {
    price = price.subtract(money);
  }

  public void rename(ProductName newName) {
    name = newName;
  }

  public void changeCategory(CategoryId newCategoryId) {
    categoryId = newCategoryId;
  }

  public void activate() {
    isActive = true;
  }

  public void deactivate() {
    isActive = false;
  }

  public static Product fromPrimitives(
      String id, String name, String categoryId, String price, boolean isActive) {
    return Product.builder()
        .id(new ProductId(UUID.fromString(id)))
        .name(new ProductName(name))
        .categoryId(new CategoryId(UUID.fromString(categoryId)))
        .price(new Money(new BigDecimal(price)))
        .isActive(isActive)
        .build();
  }

  public Map<String, Object> toPrimitives() {
    return Map.of(
        "id", id.value().toString(),
        "name", name.value(),
        "categoryId", categoryId.value().toString(),
        "price", price.value().toString(),
        "isActive", isActive);
  }

  public static Builder builder() {
    return new Builder();
  }

  public ProductId getId() {
    return id;
  }

  public ProductName getName() {
    return name;
  }

  public CategoryId getCategoryId() {
    return categoryId;
  }

  public Money getPrice() {
    return price;
  }

  public boolean isActive() {
    return isActive;
  }

  public static final class Builder {
    private ProductId id;
    private ProductName name;
    private CategoryId categoryId;
    private Money price;
    private boolean isActive;

    private Builder() {}

    public Builder id(ProductId val) {
      id = val;
      return this;
    }

    public Builder name(ProductName val) {
      name = val;
      return this;
    }

    public Builder categoryId(CategoryId val) {
      categoryId = val;
      return this;
    }

    public Builder price(Money val) {
      price = val;
      return this;
    }

    public Builder isActive(boolean val) {
      isActive = val;
      return this;
    }

    public Product build() {
      return new Product(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return isActive == product.isActive
        && Objects.equals(id, product.id)
        && Objects.equals(categoryId, product.categoryId)
        && Objects.equals(name, product.name)
        && Objects.equals(price, product.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, categoryId, name, price, isActive);
  }
}
