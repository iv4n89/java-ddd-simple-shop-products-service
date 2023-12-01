package org.ddd.product.application.usecases;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.product.application.exceptions.ProductNotFoundException;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.valueobject.ProductId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductRenamer {

  private final ProductRepository productRepository;

  public void rename(UUID productId, String newName) {
      productRepository.findById(new ProductId(productId)).ifPresentOrElse(
          product -> {
            product.rename(new ProductName(newName));
            productRepository.save(product);
          },
          () -> {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
          }
      );
  }
}
