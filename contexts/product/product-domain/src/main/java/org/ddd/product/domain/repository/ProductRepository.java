package org.ddd.product.domain.repository;

import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.shared.domain.valueobject.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(ProductId id);
    Optional<Product> findByName(ProductName name);
    Product save(Product product);

    boolean existsById(ProductId id);
}
