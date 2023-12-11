package org.ddd.product.infrastructure.persistence.messaging;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "product-service")
public class ProductKafkaConfig {
    private String productCreatedTopic;
    private String categoryCreatedTopic;
    private String categoryCreatedGroupId;
}
