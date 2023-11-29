package org.ddd.shared.infrastructure.messaging.kafka;

public interface KafkaProducer<T> {
    void send(String topic, T payload);
}
