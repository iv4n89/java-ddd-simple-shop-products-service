package org.ddd.category.infrastructure.persistence.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ddd.category.domain.events.CategoryCreatedEvent;
import org.ddd.category.domain.events.CategoryCreatedEventPublisher;
import org.ddd.shared.infrastructure.kafka.KafkaProducer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@RequiredArgsConstructor
@Component
public class CategoryCreatedKafkaEventPublisher implements CategoryCreatedEventPublisher {

  private final KafkaProducer<CategoryCreatedEvent> kafkaProducer;
  private final CategoryKafkaConfig categoryKafkaConfig;

  @Override
  public void publish(CategoryCreatedEvent event) {
    try {
      log.info("Sending message: {} to topic: {}", event, categoryKafkaConfig.getCategoryCreatedTopic());
      kafkaProducer.send(categoryKafkaConfig.getCategoryCreatedTopic(), event);
    } catch (Exception ex) {
      log.error("Error sending payload = {} for topic = {}", event, categoryKafkaConfig.getCategoryCreatedTopic());
    }
  }
}
