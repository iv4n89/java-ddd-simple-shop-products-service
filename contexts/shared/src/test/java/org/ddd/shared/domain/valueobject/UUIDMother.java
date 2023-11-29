package org.ddd.shared.domain.valueobject;

import java.util.UUID;

public class UUIDMother {

  public static String random() {
    return UUID.randomUUID().toString();
  }
}
