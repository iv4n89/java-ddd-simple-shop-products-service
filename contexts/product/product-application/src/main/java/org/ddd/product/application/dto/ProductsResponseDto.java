package org.ddd.product.application.dto;

import java.util.List;
import lombok.*;
import org.ddd.product.domain.model.Product;

@EqualsAndHashCode()
@Getter
@AllArgsConstructor
public class ProductsResponseDto {
  private final List<ProductResponseDto> responses;

  public static ProductsResponseDto fromProducts(List<Product> products) {
    List<ProductResponseDto> responses =
        products.stream()
            .map(
                product ->
                    ProductResponseDto.builder()
                        .id(product.getId().value().toString())
                        .name(product.getName().value())
                        .price(product.getPrice().value().toPlainString())
                        .categoryId(product.getCategoryId().value().toString())
                        .isActive(product.isActive())
                        .build())
            .toList();
    return new ProductsResponseDto(responses);
  }
}
