package org.ddd.product.application.dto;

import lombok.*;
import org.ddd.product.domain.model.Product;
import org.ddd.shared.application.Response;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@Builder
public class ProductResponseDto extends Response {
  private final String id;
  private final String name;
  private final String categoryId;
  private final String price;
  private final boolean isActive;

  public static ProductResponseDto fromProduct(Product product) {
    return ProductResponseDto.builder()
        .id(product.getId().value().toString())
        .name(product.getName().value())
        .price(product.getPrice().value().toPlainString())
        .categoryId(product.getCategoryId().value().toString())
        .isActive(product.isActive())
        .build();
  }
}
