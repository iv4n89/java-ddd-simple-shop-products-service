package org.ddd.shared.domain.valueobject;

import java.util.UUID;

public class CategoryIdMother {

  public static CategoryId create(UUID value) {
    return new CategoryId(value);
  }

  public static CategoryId random() {
    return create(UUID.fromString(UUIDMother.random()));
  }
}
