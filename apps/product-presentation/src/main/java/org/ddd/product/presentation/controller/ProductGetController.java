package org.ddd.product.presentation.controller;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.ddd.product.application.dto.ProductResponseDto;
import org.ddd.product.application.dto.ProductsResponseDto;
import org.ddd.product.application.usecases.ProductFinder;
import org.ddd.product.domain.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductGetController {

  private final ProductFinder productFinder;

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> findAll() {
    List<Product> products = productFinder.find();
    ProductsResponseDto response = ProductsResponseDto.fromProducts(products);
    return ResponseEntity.ok(response.getResponses());
  }

  @GetMapping("/find/{id}")
  public ResponseEntity<ProductResponseDto> findById(@PathVariable UUID id) {
    Product product = productFinder.find(id);
    ProductResponseDto response = ProductResponseDto.fromProduct(product);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ProductResponseDto> findByName(@PathVariable String name) {
    Product product = productFinder.find(name);
    ProductResponseDto response = ProductResponseDto.fromProduct(product);
    return ResponseEntity.ok(response);
  }
}
