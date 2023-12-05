package org.ddd.category.presentation.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.category.application.usecases.CategoryActivator;
import org.ddd.category.application.usecases.CategoryRenamer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/categories")
@RequiredArgsConstructor
@RestController
public class CategoryPutController {

  private final CategoryActivator categoryActivator;
  private final CategoryRenamer categoryRenamer;

  @PutMapping("/{id}/activate")
  public ResponseEntity<Void> activate(@PathVariable UUID id) {
    categoryActivator.activate(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/deactivate")
  public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
    categoryActivator.deactivate(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/rename")
  public ResponseEntity<Void> rename(@PathVariable UUID id, @RequestBody RenameRequest request) {
    categoryRenamer.rename(id, request.name());
    return ResponseEntity.noContent().build();
  }

  private record RenameRequest(String name) {}
}
