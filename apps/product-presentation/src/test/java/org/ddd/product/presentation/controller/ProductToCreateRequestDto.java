package org.ddd.product.presentation.controller;

import org.ddd.product.domain.model.Product;

import java.util.Map;

public class ProductToCreateRequestDto {
    public static Map<String, Object> productToCreateRequestDto(Product product) {
        return Map.of(
                "id", product.getId().value().toString(),
                "name", product.getName().value(),
                "price", product.getPrice().value().toPlainString(),
                "categoryId", product.getCategoryId().value().toString(),
                "active", product.isActive()
        );
    }
}
