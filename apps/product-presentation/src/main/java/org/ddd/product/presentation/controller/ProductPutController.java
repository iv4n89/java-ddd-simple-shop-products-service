package org.ddd.product.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.ddd.product.application.usecases.ProductActivator;
import org.ddd.product.application.usecases.ProductPriceChanger;
import org.ddd.product.application.usecases.ProductRenamer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductPutController {

  private final ProductActivator productActivator;
  private final ProductRenamer productRenamer;
  private final ProductPriceChanger productPriceChanger;

  @PutMapping("/{id}/activate")
  public ResponseEntity<Void> activate(@PathVariable UUID id) {
    productActivator.activate(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/deactivate")
  public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
    productActivator.deactivate(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/rename")
  public ResponseEntity<Void> rename(@PathVariable UUID id, @RequestBody RenameRequest request) {
    productRenamer.rename(id, request.name());
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/increase-price")
  public ResponseEntity<Void> increasePrice(
      @PathVariable UUID id, @RequestBody PriceRequest request) {
    productPriceChanger.increasePrice(id, request.price());
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/decrease-price")
  public ResponseEntity<Void> decreasePrice(
      @PathVariable UUID id, @RequestBody PriceRequest request) {
    productPriceChanger.decreasePrice(id, request.price());
    return ResponseEntity.noContent().build();
  }

  private record RenameRequest(String name) {}

  private record PriceRequest(BigDecimal price) {}
}
