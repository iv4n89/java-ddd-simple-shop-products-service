package org.ddd.category.domain.valueobject;

import org.ddd.shared.domain.valueobject.StringValueObject;

public class CategorySlug extends StringValueObject {
  public CategorySlug(String value) {
    super(value);
  }

  @Override
  protected void isValid() {
    super.isValid();
    isFiveCharacters();
  }

  private void isFiveCharacters() {
    if (value.length() != 5) {
      throw new IllegalArgumentException("The slug must be 5 characters long");
    }
  }
}
