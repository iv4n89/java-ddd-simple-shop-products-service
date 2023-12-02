package org.ddd.product.domain.events;


public class ProductCreatedEvent extends ProductEvent {

  public ProductCreatedEvent(String productId) {
    super(productId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductCreatedEvent that)) return false;
    return getProductId().equals(that.getProductId());
  }

  @Override
  public int hashCode() {
    return getProductId().hashCode();
  }
}
