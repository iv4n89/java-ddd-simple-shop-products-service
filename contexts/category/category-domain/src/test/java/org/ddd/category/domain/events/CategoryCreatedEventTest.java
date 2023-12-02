package org.ddd.category.domain.events;

import org.ddd.category.domain.CategoryDomainTestConfig;
import org.ddd.shared.domain.valueobject.UUIDMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CategoryDomainTestConfig.class)
class CategoryCreatedEventTest {

  @Test
  void testCreateAValidCategoryCreatedEvent() {
    // Given
    String expectedId = UUIDMother.random();
    CategoryCreatedEvent expectedObject = CategoryCreatedEventMother.create(expectedId);

    // When
    CategoryCreatedEvent actualObject = new CategoryCreatedEvent(expectedId);

    // Then
    assertEquals(expectedObject, actualObject);
    assertEquals(expectedId, actualObject.getCategoryId());
    assertNotNull(actualObject.getIssedAt());
    assertTrue(actualObject.getIssedAt().isBefore(LocalDateTime.now()));
  }
}
