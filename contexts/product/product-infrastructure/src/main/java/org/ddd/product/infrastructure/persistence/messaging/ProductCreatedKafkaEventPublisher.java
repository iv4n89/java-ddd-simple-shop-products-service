package org.ddd.product.infrastructure.persistence.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ddd.product.domain.events.ProductCreatedEvent;
import org.ddd.product.domain.events.ProductCreatedEventPublisher;
import org.ddd.shared.infrastructure.messaging.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@RequiredArgsConstructor
@Component
public class ProductCreatedKafkaEventPublisher implements ProductCreatedEventPublisher {

  private final KafkaProducer<ProductCreatedEvent> kafkaProducer;

  @Value("${kafka-config.product-created-topic}")
  private String topic;

  @Override
  public void publish(ProductCreatedEvent event) {
    try {
      log.info("Sending message: {} to topic: {}", event, topic);
      kafkaProducer.send(topic, event);
    } catch (Exception ex) {
      log.error("Error sending payload = {} for topic = {}", event, topic);
    }
  }
}
