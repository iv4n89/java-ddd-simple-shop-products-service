package org.ddd.product.application;

import org.ddd.product.application.usecases.ProductCreator;
import org.ddd.product.application.usecases.ProductFinder;
import org.ddd.product.domain.events.ProductCreatedEventPublisher;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.shared.infrastructure.messaging.kafka.KafkaProducer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@SpringBootApplication(scanBasePackages = {"org.ddd.product"})
public class ProductApplicationTestConfig {

  @Bean(name = "productRepositoryTest")
  public ProductRepository productRepository() {
    return mock(ProductRepository.class);
  }


  @Bean(name = "productFinderTest")
  public ProductFinder productFinder() {
    return new ProductFinder(productRepository());
  }

  @Bean(name = "productCreatorTest")
  public ProductCreator productCreator() {
    return new ProductCreator(productRepository(), productCreatedEventPublisher());
  }

  @Bean(name = "productCreatedEventPublisherTest")
  public ProductCreatedEventPublisher productCreatedEventPublisher() {
    return mock(ProductCreatedEventPublisher.class);
  }
}
