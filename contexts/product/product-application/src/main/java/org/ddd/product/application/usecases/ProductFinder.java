package org.ddd.product.application.usecases;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.product.application.exceptions.ProductNotFoundException;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.valueobject.ProductId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductFinder {
  private final ProductRepository productRepository;

  public List<Product> find() {
    return productRepository.findAll();
  }

  public Product find(UUID id) {
    return productRepository
        .findById(new ProductId(id))
        .orElseThrow(
            () ->
                new ProductNotFoundException(
                    "Product with id %s not found".formatted(id.toString())));
  }

  public Product find(String name) {
    return productRepository
        .findByName(new ProductName(name))
        .orElseThrow(
            () -> new ProductNotFoundException("Product with name %s not found".formatted(name)));
  }
}
