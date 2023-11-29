package org.ddd.product.presentation.controller;

import java.net.URI;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ddd.product.application.dto.ProductResponseDto;
import org.ddd.product.application.usecases.ProductCreator;
import org.ddd.product.domain.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductPostController {

  private final ProductCreator productCreator;

  @PostMapping
  public ResponseEntity<ProductResponseDto> create(@RequestBody Request request) {
    Product product =
        productCreator.save(
            request.id(), request.name(), request.categoryId(), request.price(), request.active());
    ProductResponseDto response = ProductResponseDto.fromProduct(product);
    return ResponseEntity.created(URI.create("/products")).body(response);
  }

  private record Request(UUID id, String name, String price, UUID categoryId, boolean active) {}
}
