package org.ddd.shared.domain.valueobject;

import java.util.UUID;

public abstract class UUIDValueObject extends ValueObject<UUID> {

  protected UUIDValueObject(UUID value) {
    super(value);
  }

  @Override
  protected void isValid() {
    isNotNull();
    isValidUUID();
  }

  protected void isNotNull() {
    if (value == null) {
      throw new IllegalArgumentException("The value cannot be null");
    }
  }

  protected void isValidUUID() {
    if (!value.toString().matches("^[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}$")) {
      throw new IllegalArgumentException("The value is not a valid UUID");
    }
  }
}
