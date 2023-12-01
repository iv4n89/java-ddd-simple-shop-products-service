package org.ddd.product.application;

import org.ddd.product.application.usecases.*;
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

  @Bean(name = "productActivatorTest")
  public ProductActivator productActivator() {
    return new ProductActivator(productRepository());
  }

  @Bean(name = "productRenamerTest")
  public ProductRenamer productRenamer() {
    return new ProductRenamer(productRepository());
  }

  @Bean(name = "productPriceChangerTest")
  public ProductPriceChanger productPriceChanger() {
    return new ProductPriceChanger(productRepository());
  }

  @Bean(name = "productCreatedEventPublisherTest")
  public ProductCreatedEventPublisher productCreatedEventPublisher() {
    return mock(ProductCreatedEventPublisher.class);
  }
}
