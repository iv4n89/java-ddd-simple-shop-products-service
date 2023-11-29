package org.ddd.shared.domain.valueobject;

import java.util.UUID;

public class ProductIdMother {

  public static ProductId create(UUID value) {
    return new ProductId(value);
  }

  public static ProductId random() {
    return create(UUID.fromString(UUIDMother.random()));
  }
}
