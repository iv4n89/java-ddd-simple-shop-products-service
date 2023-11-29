package org.ddd.shared.domain.valueobject;

import java.math.BigDecimal;

public class Money extends ValueObject<BigDecimal> {
  public Money(BigDecimal value) {
    super(value);
  }

  public Money add(Money money) {
    return new Money(value.add(money.value));
  }

  public Money subtract(Money money) {
    return new Money(value.subtract(money.value));
  }

  public Money multiply(Money money) {
    return new Money(value.multiply(money.value));
  }

  public Money divide(Money money) {
    return new Money(value.divide(money.value));
  }

  public Money add(BigDecimal value) {
    return new Money(this.value.add(value));
  }

  public Money subtract(BigDecimal value) {
    return new Money(this.value.subtract(value));
  }

  public Money multiply(BigDecimal value) {
    return new Money(this.value.multiply(value));
  }

  public Money divide(BigDecimal value) {
    return new Money(this.value.divide(value));
  }

  public boolean isGreaterThan(Money money) {
    return value.compareTo(money.value) > 0;
  }

  public boolean isGreaterThan(BigDecimal value) {
    return this.value.compareTo(value) > 0;
  }

  public boolean isGreaterThanZero() {
    return isGreaterThan(BigDecimal.ZERO);
  }

  @Override
  protected void isValid() {
    isNotNull();
    isPositive();
  }

  protected void isNotNull() {
    if (value == null) {
      throw new IllegalArgumentException("The value cannot be null");
    }
  }

  protected void isPositive() {
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("The value cannot be negative");
    }
  }
}
