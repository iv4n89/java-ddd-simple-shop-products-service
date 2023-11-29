package org.ddd.product.infrastructure.persistence.mapper;

import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.product.infrastructure.persistence.entity.ProductEntity;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.ddd.shared.domain.valueobject.Money;
import org.ddd.shared.domain.valueobject.ProductId;

public class ProductDataMapper {

    private ProductDataMapper() {}

    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
            .id(product.getId().value())
            .name(product.getName().value())
            .price(product.getPrice().value())
            .categoryId(product.getCategoryId().value())
            .build();
    }

    public static Product toDomain(ProductEntity productEntity) {
    return Product.builder()
        .id(new ProductId(productEntity.getId()))
        .name(new ProductName(productEntity.getName()))
        .price(new Money(productEntity.getPrice()))
        .categoryId(new CategoryId(productEntity.getCategoryId()))
        .build();
    }

}
