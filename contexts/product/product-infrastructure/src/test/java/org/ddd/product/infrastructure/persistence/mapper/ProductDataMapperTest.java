package org.ddd.product.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.ProductNameMother;
import org.ddd.product.domain.model.Product;
import org.ddd.product.infrastructure.ProductInfrastructureTestConfiguration;
import org.ddd.product.infrastructure.persistence.entity.ProductEntity;
import org.ddd.shared.domain.valueobject.CategoryIdMother;
import org.ddd.shared.domain.valueobject.MoneyMother;
import org.ddd.shared.domain.valueobject.ProductIdMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(classes = ProductInfrastructureTestConfiguration.class)
@EmbeddedKafka(
    partitions = 1,
    topics = {"product-created-event"})
@TestPropertySource(
    properties = {
      "kafka-config.bootstrap-servers=${spring.embedded.kafka.brokers}",
      "kafka-config.product-created-topic=product-created-topic"
    })
class ProductDataMapperTest {

  @Test
  void testMapperShouldMapToEntity() {
    Product product = ProductMother.randomActive();
    ProductEntity productEntity = ProductDataMapper.toEntity(product);
    assertEquals(product.getId().value(), productEntity.getId());
    assertEquals(product.getName().value(), productEntity.getName());
    assertEquals(product.getPrice().value(), productEntity.getPrice());
    assertEquals(product.getCategoryId().value(), productEntity.getCategoryId());
  }

  @Test
  void testMapperShouldMapToDomain() {
    ProductEntity productEntity =
        ProductEntity.builder()
            .id(ProductIdMother.random().value())
            .name(ProductNameMother.random().value())
            .price(MoneyMother.random().value())
            .categoryId(CategoryIdMother.random().value())
            .isActive(true)
            .build();
    Product product = ProductDataMapper.toDomain(productEntity);
    assertEquals(productEntity.getId(), product.getId().value());
    assertEquals(productEntity.getName(), product.getName().value());
    assertEquals(productEntity.getPrice(), product.getPrice().value());
    assertEquals(productEntity.getCategoryId(), product.getCategoryId().value());
    assertTrue(product.isActive());
  }
}
