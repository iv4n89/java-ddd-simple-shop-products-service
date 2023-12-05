package org.ddd.category.infrastructure.persistence.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ddd.category.domain.events.CategoryCreatedEvent;
import org.ddd.category.domain.events.CategoryCreatedEventPublisher;
import org.ddd.shared.infrastructure.messaging.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@RequiredArgsConstructor
@Component
public class CategoryCreatedKafkaEventPublisher implements CategoryCreatedEventPublisher {

  private final KafkaProducer<CategoryCreatedEvent> kafkaProducer;

  @Value(("${kafka-config.category-created-topic}"))
  private String topic;

  @Override
  public void publish(CategoryCreatedEvent event) {
    try {
      log.info("Sending message: {} to topic: {}", event, topic);
      kafkaProducer.send(topic, event);
    } catch (Exception ex) {
      log.error("Error sending payload = {} for topic = {}", event, topic);
    }
  }
}
