package org.ddd.shared.infrastructure.messaging.kafka;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerImpl<T> implements KafkaProducer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void send(String topic, T payload) {
        log.info("Sending message: {} to topic: {}", payload, topic);
        try {
            kafkaTemplate.send(topic, payload);
        } catch (Exception ex) {
            log.error("Error sending payload = {} for topic = {}", payload, topic);
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer");
            kafkaTemplate.destroy();
        }
    }
}
