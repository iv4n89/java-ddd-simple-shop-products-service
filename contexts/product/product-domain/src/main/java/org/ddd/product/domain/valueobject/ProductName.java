package org.ddd.product.domain.valueobject;

import org.ddd.shared.domain.valueobject.StringValueObject;

public class ProductName extends StringValueObject {
  public ProductName(String value) {
    super(value);
  }

  @Override
  protected void isValid() {
    super.isValid();
    isLengthGreaterThanThree();
    isLengthLessThanFifty();
  }

  protected void isLengthGreaterThanThree() {
    if (value.length() < 3) {
      throw new IllegalArgumentException("The value is not greater than five");
    }
  }

  protected void isLengthLessThanFifty() {
    if (value.length() > 50) {
      throw new IllegalArgumentException("The value is not less than fifty");
    }
  }
}
