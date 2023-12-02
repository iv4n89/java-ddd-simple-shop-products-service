package org.ddd.category.domain.valueobject;

import org.ddd.shared.domain.valueobject.StringValueObject;

public class CategoryName extends StringValueObject {
  public CategoryName(String value) {
    super(value);
  }

  @Override
  protected void isValid() {
    super.isValid();
    isLessThanThreeCharacters();
    isGreaterThanThirtyCharacters();
  }

  private void isLessThanThreeCharacters() {
    if (value.length() < 3) {
      throw new IllegalArgumentException("The name must be at least 3 characters long");
    }
  }

  private void isGreaterThanThirtyCharacters() {
    if (value.length() > 30) {
      throw new IllegalArgumentException("The name must be less than 30 characters long");
    }
  }
}
