package org.ddd.product.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.ddd.product.domain.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class ProductEntity {

  @Id private UUID id;

  private String name;

  private BigDecimal price;

  private UUID categoryId;

  private boolean isActive;
}
