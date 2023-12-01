package org.ddd.product.application.usecases;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.product.application.exceptions.ProductNotFoundException;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.shared.domain.valueobject.ProductId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductActivator {

  private final ProductRepository productRepository;

  public void activate(UUID productId) {
    productRepository.findById(new ProductId(productId)).ifPresentOrElse(
        product -> {
          product.activate();
          productRepository.save(product);
        },
        () -> {
          throw new ProductNotFoundException("Product with id " + productId + " not found");
        }
    );
  }

  public void deactivate(UUID productId) {
    productRepository.findById(new ProductId(productId)).ifPresentOrElse(
        product -> {
          product.deactivate();
          productRepository.save(product);
        },
        () -> {
          throw new ProductNotFoundException("Product with id " + productId + " not found");
        }
    );
  }
}
