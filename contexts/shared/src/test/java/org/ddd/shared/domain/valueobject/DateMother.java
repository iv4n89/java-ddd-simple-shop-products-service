package org.ddd.shared.domain.valueobject;

import java.util.Date;

public class DateMother {

  public static Date random() {
    return MotherCreator.random().date().birthday();
  }
}
