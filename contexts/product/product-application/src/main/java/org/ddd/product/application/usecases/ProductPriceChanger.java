package org.ddd.product.application.usecases;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.product.application.exceptions.ProductNotFoundException;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.shared.domain.valueobject.Money;
import org.ddd.shared.domain.valueobject.ProductId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductPriceChanger {

  private final ProductRepository productRepository;

  public void increasePrice(UUID productId, BigDecimal newPrice) {
    productRepository
        .findById(new ProductId(productId))
        .ifPresentOrElse(
            product -> {
              product.increasePrice(new Money(newPrice));
              productRepository.save(product);
            },
            () -> {
              throw new ProductNotFoundException("Product with id " + productId + " not found");
            });
  }

  public void decreasePrice(UUID productId, BigDecimal newPrice) {
    productRepository
        .findById(new ProductId(productId))
        .ifPresentOrElse(
            product -> {
              product.decreasePrice(new Money(newPrice));
              productRepository.save(product);
            },
            () -> {
              throw new ProductNotFoundException("Product with id " + productId + " not found");
            });
  }
}
