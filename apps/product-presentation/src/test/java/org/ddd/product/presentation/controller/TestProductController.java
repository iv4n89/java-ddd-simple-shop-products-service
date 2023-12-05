package org.ddd.product.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.ProductNameMother;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.product.presentation.ProductTestConfig;
import org.ddd.shared.domain.valueobject.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@EmbeddedKafka(
    partitions = 1,
    topics = {"product-created-topic"})
@TestPropertySource(
    properties = {
      "kafka-config.bootstrap-servers=${spring.embedded.kafka.brokers}",
      "kafka-config.product-created-topic=product-created-topic"
    })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestProductController {

  @Autowired private WebTestClient client;

  private ObjectMapper objectMapper;

  private ProductId testProductId;
  private ProductName testProductName;
  private Money testProductPrice;
  private CategoryId testProductCategoryId;

  @BeforeAll
  void beforeAll() {
    testProductId = ProductIdMother.random();
    testProductName = ProductNameMother.random();
    testProductPrice = MoneyMother.random();
    testProductCategoryId = CategoryIdMother.random();
  }

  @BeforeEach
  void setUp() {
    this.objectMapper = new ObjectMapper();
  }

  @Test
  @Order(1)
  void testCreateAProduct() {
    Product product =
        ProductMother.from(
            testProductId, testProductName, testProductCategoryId, testProductPrice, true);

    Map<String, Object> primitives = ProductToCreateRequestDto.productToCreateRequestDto(product);

    client
        .post()
        .uri("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(primitives)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(product.getId().value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(product.getName().value()))
        .jsonPath("$.price")
        .value(Matchers.is(product.getPrice().value().toPlainString()))
        .jsonPath("$.categoryId")
        .value(Matchers.is(product.getCategoryId().value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(5)
  void testUpdateProduct() {
    testProductName = ProductNameMother.random();
    testProductPrice = MoneyMother.random();
    Product product =
        ProductMother.from(
            testProductId, testProductName, testProductCategoryId, testProductPrice, true);

    Map<String, Object> primitives = ProductToCreateRequestDto.productToCreateRequestDto(product);

    client
        .post()
        .uri("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(primitives)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(product.getId().value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(product.getName().value()))
        .jsonPath("$.price")
        .value(Matchers.is(product.getPrice().value().toPlainString()))
        .jsonPath("$.categoryId")
        .value(Matchers.is(product.getCategoryId().value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(2)
  void testFindById() {
    client
        .get()
        .uri("/products/find/{id}", testProductId.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testProductId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(testProductName.value()))
        .jsonPath("$.price")
        .value(Matchers.is(testProductPrice.value().toPlainString() + "0"))
        .jsonPath("$.categoryId")
        .value(Matchers.is(testProductCategoryId.value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(3)
  void testFindByName() {
    client
        .get()
        .uri("/products/name/{name}", testProductName.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testProductId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(testProductName.value()))
        .jsonPath("$.price")
        .value(Matchers.is(testProductPrice.value().toPlainString() + "0"))
        .jsonPath("$.categoryId")
        .value(Matchers.is(testProductCategoryId.value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(4)
  void testFindAll() throws JsonProcessingException {

    client
        .get()
        .uri("/products")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$")
        .isArray()
        .jsonPath("$", Matchers.hasSize(1))
        .exists()
        .jsonPath("$[0].id")
        .value(Matchers.is(testProductId.value().toString()))
        .jsonPath("$[0].name")
        .value(Matchers.is(testProductName.value()))
        .jsonPath("$[0].price")
        .value(Matchers.is(testProductPrice.value().toPlainString() + "0"))
        .jsonPath("$[0].categoryId")
        .value(Matchers.is(testProductCategoryId.value().toString()))
        .jsonPath("$[0].active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(6)
  void testDeactivateProduct() {
    client
        .put()
        .uri("/products/{id}/deactivate", testProductId.value())
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();
  }

  @Test
  @Order(7)
  void testProductIsDeactivated() {
    client
        .get()
        .uri("/products/find/{id}", testProductId.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testProductId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(testProductName.value()))
        .jsonPath("$.price")
        .value(Matchers.is(testProductPrice.value().toPlainString() + "0"))
        .jsonPath("$.categoryId")
        .value(Matchers.is(testProductCategoryId.value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(false));
  }

  @Test
  @Order(8)
  void testActivateProduct() {
    client
        .put()
        .uri("/products/{id}/activate", testProductId.value())
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();
  }

  @Test
  @Order(9)
  void testProductIsActivated() {
    client
        .get()
        .uri("/products/find/{id}", testProductId.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testProductId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(testProductName.value()))
        .jsonPath("$.price")
        .value(Matchers.is(testProductPrice.value().toPlainString() + "0"))
        .jsonPath("$.categoryId")
        .value(Matchers.is(testProductCategoryId.value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(10)
  void testRenameProduct() {
    testProductName = ProductNameMother.random();

    Map<String, String> request = Map.of("name", testProductName.value());

    client
        .put()
        .uri("/products/{id}/rename", testProductId.value())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();

    client
        .get()
        .uri("/products/find/{id}", testProductId.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.name")
        .value(Matchers.is(testProductName.value()));
  }

  @Test
  @Order(11)
  void testIncreasePrice() {
    Money toAdd = MoneyMother.random();
    Money expected = testProductPrice.add(toAdd);

    Map<String, BigDecimal> request = Map.of("price", toAdd.value());

    client
        .put()
        .uri("/products/{id}/increase-price", testProductId.value())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();

    client
        .get()
        .uri("/products/find/{id}", testProductId.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.price")
        .value(Matchers.is(expected.value().toPlainString() + "0"));

    testProductPrice = expected;
  }

  @Test
  @Order(12)
  void testDecreasePrice() {
    Money toSubtract = testProductPrice.divide(BigDecimal.valueOf(2));
    Money expected = testProductPrice.subtract(toSubtract);

    Map<String, BigDecimal> request = Map.of("price", toSubtract.value());

    client
        .put()
        .uri("/products/{id}/decrease-price", testProductId.value())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();

    client
        .get()
        .uri("/products/find/{id}", testProductId.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.price")
        .value(Matchers.is(expected.value().toPlainString() + "0"));

    testProductPrice = expected;
  }
}
