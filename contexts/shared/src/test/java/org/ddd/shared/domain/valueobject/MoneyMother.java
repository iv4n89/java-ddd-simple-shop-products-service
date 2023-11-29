package org.ddd.shared.domain.valueobject;

import java.math.BigDecimal;

public class MoneyMother {

  public static Money create(BigDecimal value) {
    return new Money(value);
  }

  public static Money random() {
    double randomValue = Math.random() * 1000;
    return create(BigDecimal.valueOf(randomValue));
  }

  public static Money zero() {
    return create(BigDecimal.ZERO);
  }

  public static Money one() {
    return create(BigDecimal.ONE);
  }

  public static Money half(Money value) {
    return create(value.value().divide(BigDecimal.valueOf(2)));
  }
}
