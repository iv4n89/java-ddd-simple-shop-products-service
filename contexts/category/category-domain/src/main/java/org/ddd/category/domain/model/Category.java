package org.ddd.category.domain.model;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.shared.domain.AggregateRoot;
import org.ddd.shared.domain.valueobject.CategoryId;

public class Category implements AggregateRoot {

  private final CategoryId id;
  private final CategoryName name;
  private final CategorySlug slug;
  private final Integer numOfProducts;
  private boolean isActive;

  private Category(Builder builder) {
    id = builder.id;
    name = builder.name;
    slug = builder.slug;
    numOfProducts = builder.numOfProducts;
    isActive = builder.isActive;
  }

  public static Category fromPrimitives(
      String id, String name, String slug, Integer numOfProducts, boolean isActive) {
    return Category.builder()
        .id(new CategoryId(UUID.fromString(id)))
        .name(new CategoryName(name))
        .slug(new CategorySlug(slug))
        .numOfProducts(numOfProducts)
        .isActive(isActive)
        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public void activate() {
    this.isActive = true;
  }

  public Category rename(String newName) {
    return Category.builder()
        .id(this.id)
        .name(new CategoryName(newName))
        .slug(this.slug)
        .numOfProducts(this.numOfProducts)
        .isActive(this.isActive)
        .build();
  }

  public Category changeSlug(String newSlug) {
    return Category.builder()
        .id(this.id)
        .name(this.name)
        .slug(new CategorySlug(newSlug))
        .numOfProducts(this.numOfProducts)
        .isActive(this.isActive)
        .build();
  }

  public Category changeNumOfProducts(Integer newNumOfProducts) {
    return Category.builder()
        .id(this.id)
        .name(this.name)
        .slug(this.slug)
        .numOfProducts(newNumOfProducts)
        .isActive(this.isActive)
        .build();
  }

  public Map<String, Object> toPrimitives() {
    return Map.of(
        "id", this.id.value().toString(),
        "name", this.name.value(),
        "slug", this.slug.value(),
        "numOfProducts", this.numOfProducts,
        "isActive", this.isActive);
  }

  public void deactivate() {
    this.isActive = false;
  }

  public CategoryId getId() {
    return id;
  }

  public CategoryName getName() {
    return name;
  }

  public CategorySlug getSlug() {
    return slug;
  }

  public Integer getNumOfProducts() {
    return numOfProducts;
  }

  public boolean isActive() {
    return isActive;
  }

  public static final class Builder {
    private CategoryId id;
    private CategoryName name;
    private CategorySlug slug;
    private Integer numOfProducts;
    private boolean isActive;

    private Builder() {}

    public Builder id(CategoryId val) {
      id = val;
      return this;
    }

    public Builder name(CategoryName val) {
      name = val;
      return this;
    }

    public Builder slug(CategorySlug val) {
      slug = val;
      return this;
    }

    public Builder numOfProducts(Integer val) {
      numOfProducts = val;
      return this;
    }

    public Builder isActive(boolean val) {
      isActive = val;
      return this;
    }

    public Category build() {
      return new Category(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return isActive == category.isActive
        && Objects.equals(id, category.id)
        && Objects.equals(name, category.name)
        && Objects.equals(slug, category.slug)
        && Objects.equals(numOfProducts, category.numOfProducts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, slug, numOfProducts, isActive);
  }
}
