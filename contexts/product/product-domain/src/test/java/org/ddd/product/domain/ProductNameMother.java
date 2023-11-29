package org.ddd.product.domain;

import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.valueobject.WordMother;

public class ProductNameMother {

  public static ProductName create(String value) {
    return new ProductName(value);
  }

  public static ProductName random() {
    return create(WordMother.randomMin(5));
  }
}
