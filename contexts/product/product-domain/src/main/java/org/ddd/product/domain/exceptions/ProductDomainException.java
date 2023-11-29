package org.ddd.product.domain.exceptions;

import org.ddd.shared.domain.exceptions.DomainException;

public class ProductDomainException extends DomainException {
  public ProductDomainException(String message) {
    super(message);
  }

  public ProductDomainException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductDomainException(Throwable cause) {
    super(cause);
  }

  public ProductDomainException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
