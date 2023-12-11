package org.ddd.category.infrastructure.persistence.messaging;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "category-service")
public class CategoryKafkaConfig {
    private String categoryCreatedTopic;
    private String productCreatedTopic;
    private String productCreatedGroupId;
}
