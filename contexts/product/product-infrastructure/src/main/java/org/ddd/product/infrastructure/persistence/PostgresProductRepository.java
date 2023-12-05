package org.ddd.product.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.product.infrastructure.persistence.entity.ProductEntity;
import org.ddd.product.infrastructure.persistence.mapper.ProductDataMapper;
import org.ddd.shared.domain.valueobject.ProductId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@RequiredArgsConstructor
@Component("PostgresProductRepository")
public class PostgresProductRepository implements ProductRepository {

  private final ProductJpaRepository productJpaRepository;

  @Override
  public List<Product> findAll() {
    return productJpaRepository.findAll().stream().map(ProductDataMapper::toDomain).toList();
  }

  @Override
  public Optional<Product> findById(ProductId id) {
    return productJpaRepository.findById(id.value()).map(ProductDataMapper::toDomain);
  }

  @Override
  public Optional<Product> findByName(ProductName name) {
    return productJpaRepository.findByName(name.value()).map(ProductDataMapper::toDomain);
  }

  @Override
  public Product save(Product product) {
    ProductEntity productEntity = ProductDataMapper.toEntity(product);
    return ProductDataMapper.toDomain(productJpaRepository.save(productEntity));
  }

  @Override
  public boolean existsById(ProductId id) {
    return productJpaRepository.existsById(id.value());
  }
}
