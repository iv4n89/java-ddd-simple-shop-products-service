package org.ddd.category.domain.exceptions;

import org.ddd.shared.domain.exceptions.DomainException;

public class CategoryDomainException extends DomainException {
  public CategoryDomainException(String message) {
    super(message);
  }

  public CategoryDomainException(String message, Throwable cause) {
    super(message, cause);
  }

  public CategoryDomainException(Throwable cause) {
    super(cause);
  }

  public CategoryDomainException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
