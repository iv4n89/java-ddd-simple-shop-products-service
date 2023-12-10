package org.ddd.category.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.model.CategoryMother;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategoryNameMother;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.category.domain.valueobject.CategorySlugMother;
import org.ddd.category.presentation.CategoryTestConfig;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.ddd.shared.domain.valueobject.CategoryIdMother;
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
    topics = {"category-created-topic"},
    partitions = 1)
@TestPropertySource(
    properties = {
      "kafka-config.bootstrap-servers=${spring.embedded.kafka.brokers}",
      "kafka-config.category-created-topic=category-created-topic"
    })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = CategoryTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCategoryController {

  @Autowired private WebTestClient client;

  private ObjectMapper objectMapper;

  private CategoryId testCategoryId;
  private CategoryName testCategoryName;
  private CategorySlug testCategorySlug;

  @BeforeAll
  void beforeAll() {
    testCategoryId = CategoryIdMother.random();
    testCategoryName = CategoryNameMother.random();
    testCategorySlug = CategorySlugMother.random();
  }

  @BeforeEach
  void beforeEach() {
    objectMapper = new ObjectMapper();
  }

  @Test
  @Order(1)
  void testCreateACategory() {
    Category category =
        CategoryMother.create(testCategoryId, testCategoryName, testCategorySlug, 0, true);
    Map<String, Object> primitives =
        CategoryToCreateRequestDto.categoryToCreateRequestDto(category);

    client
        .post()
        .uri("/categories")
        .bodyValue(primitives)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(testCategoryId.value().toString())
        .jsonPath("$.name")
        .isEqualTo(testCategoryName.value())
        .jsonPath("$.slug")
        .isEqualTo(testCategorySlug.value())
        .jsonPath("$.active")
        .isEqualTo(true);
  }

  @Test
  @Order(2)
  void testFindById() {
    client
        .get()
        .uri("/categories/find/{id}", testCategoryId.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(testCategoryName.value()))
        .jsonPath("$.slug")
        .value(Matchers.is(testCategorySlug.value()))
        .jsonPath("$.numberOfProducts")
        .value(Matchers.is(0))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(3)
  void testFindByName() {
    client
        .get()
        .uri("/categories/name/{name}", testCategoryName.value())
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(testCategoryName.value()))
        .jsonPath("$.slug")
        .value(Matchers.is(testCategorySlug.value()))
        .jsonPath("$.numberOfProducts")
        .value(Matchers.is(0))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(4)
  void testFindBySlug() {
    client
        .get()
        .uri("/categories/slug/{slug}", testCategorySlug.value())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(testCategoryName.value()))
        .jsonPath("$.slug")
        .value(Matchers.is(testCategorySlug.value()))
        .jsonPath("$.numberOfProducts")
        .value(Matchers.is(0))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(6)
  void testFindAll() {
    client
        .get()
        .uri("/categories")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$")
        .isArray()
        .jsonPath("$")
        .value(Matchers.hasSize(1))
        .jsonPath("$[0].id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$[0].name")
        .value(Matchers.is(testCategoryName.value()))
        .jsonPath("$[0].slug")
        .value(Matchers.is(testCategorySlug.value()))
        .jsonPath("$[0].numberOfProducts")
        .value(Matchers.is(0))
        .jsonPath("$[0].active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(5)
  void testUpdateProduct() {
    testCategoryName = CategoryNameMother.random();
    testCategorySlug = CategorySlugMother.random();
    Category category =
        CategoryMother.create(testCategoryId, testCategoryName, testCategorySlug, 0, true);

    Map<String, Object> primitives =
        CategoryToCreateRequestDto.categoryToCreateRequestDto(category);

    client
        .post()
        .uri("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(primitives)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.is(testCategoryName.value()))
        .jsonPath("$.slug")
        .value(Matchers.is(testCategorySlug.value()))
        .jsonPath("$.numberOfProducts")
        .value(Matchers.is(0))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(7)
  void testDeactivateCategory() {
    client
        .get()
        .uri("/categories/find/{id}", testCategoryId.value().toString())
        .exchange()
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(true));

    client
        .put()
        .uri("/categories/{id}/deactivate", testCategoryId.value().toString())
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();

    client
        .get()
        .uri("/categories/find/{id}", testCategoryId.value().toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(false));
  }

  @Test
  @Order(8)
  void testActivateProduct() {
    client
        .get()
        .uri("/categories/find/{id}", testCategoryId.value().toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(false));

    client
        .put()
        .uri("/categories/{id}/activate", testCategoryId.value().toString())
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();

    client
        .get()
        .uri("/categories/find/{id}", testCategoryId.value().toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.active")
        .value(Matchers.is(true));
  }

  @Test
  @Order(9)
  void testRenameCategory() {

    CategoryName newName = CategoryNameMother.random();

    Map<String, Object> request = Map.of("name", newName.value());

    client
        .get()
        .uri("/categories/find/{id}", testCategoryId.value().toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("name")
        .value(Matchers.is(testCategoryName.value()));

    client
        .put()
        .uri("/categories/{id}/rename", testCategoryId.value().toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();

    client
        .get()
        .uri("/categories/find/{id}", testCategoryId.value().toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .value(Matchers.is(testCategoryId.value().toString()))
        .jsonPath("$.name")
        .value(Matchers.not(testCategoryName.value()))
        .jsonPath("$.name")
        .value(Matchers.is(newName.value()));
  }
}
