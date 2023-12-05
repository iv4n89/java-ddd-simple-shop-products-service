package org.ddd.category.domain.valueobject;

import org.ddd.shared.domain.valueobject.StringValueObject;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CategoryName)) return false;
    CategoryName that = (CategoryName) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
