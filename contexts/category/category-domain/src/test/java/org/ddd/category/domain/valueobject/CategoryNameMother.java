package org.ddd.category.domain.valueobject;

import org.ddd.shared.domain.valueobject.WordMother;

public class CategoryNameMother {

  public static CategoryName random() {
    return create(WordMother.randomMinMax(3, 30));
  }

  public static CategoryName create(String value) {
    return new CategoryName(value);
  }
}
