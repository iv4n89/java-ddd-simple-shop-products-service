package org.ddd.category.domain.model;

import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategoryNameMother;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.category.domain.valueobject.CategorySlugMother;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.ddd.shared.domain.valueobject.CategoryIdMother;
import org.ddd.shared.domain.valueobject.IntegerMother;

public class CategoryMother {

  public static Category create(
      CategoryId id,
      CategoryName name,
      CategorySlug slug,
      Integer numOfProducts,
      boolean isActivate) {
    return Category.builder()
        .id(id)
        .name(name)
        .slug(slug)
        .numOfProducts(numOfProducts)
        .isActive(isActivate)
        .build();
  }

  public static Category random() {
    return create(
        CategoryIdMother.random(), CategoryNameMother.random(), CategorySlugMother.random(), IntegerMother.random(), true);
  }

  public static Category randomInactive() {
    return create(
        CategoryIdMother.random(), CategoryNameMother.random(), CategorySlugMother.random(), IntegerMother.random(), false);
  }
}
