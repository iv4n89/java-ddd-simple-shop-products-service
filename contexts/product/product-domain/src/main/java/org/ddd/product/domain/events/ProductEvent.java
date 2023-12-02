package org.ddd.product.domain.events;

import java.time.LocalDateTime;
import org.ddd.product.domain.model.Product;
import org.ddd.shared.domain.events.DomainEvent;

public abstract class ProductEvent implements DomainEvent<Product> {

  private final String productId;
  private final LocalDateTime issedAt;

  protected ProductEvent(String productId) {
    this.productId = productId;
    this.issedAt = LocalDateTime.now();
  }

  public String getProductId() {
    return productId;
  }

  public LocalDateTime getIssedAt() {
    return issedAt;
  }
}
