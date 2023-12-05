package org.ddd.category.application;

import org.ddd.category.application.usecases.*;
import org.ddd.category.domain.events.CategoryCreatedEventPublisher;
import org.ddd.category.domain.repository.CategoryRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@SpringBootApplication(scanBasePackages = {"org.ddd.category"})
public class CategoryApplicationTestConfig {

  @Bean(name = "categoryRepositoryTest")
  public CategoryRepository categoryRepository() {
    return mock(CategoryRepository.class);
  }

  @Bean(name = "categoryFinderTest")
  public CategoryFinder categoryFinder() {
    return new CategoryFinder(categoryRepository());
  }

  @Bean(name = "categoryCreatorTest")
  public CategoryCreator categoryCreator() {
    return new CategoryCreator(categoryRepository(), categoryCreatedEventPublisher());
  }

  @Bean(name = "categoryRenamerTest")
  public CategoryRenamer categoryRenamer() {
    return new CategoryRenamer(categoryRepository());
  }

  @Bean(name = "categoryActivatorTest")
  public CategoryActivator categoryActivator() {
    return new CategoryActivator(categoryRepository());
  }

  @Bean(name = "categoryNumOfProductsChangerTest")
    public CategoryNumOfProductsChanger categoryNumOfProductsChanger() {
        return new CategoryNumOfProductsChanger(categoryRepository());
    }

  @Bean(name = "categoryCreatedEventPublisherTest")
  public CategoryCreatedEventPublisher categoryCreatedEventPublisher() {
    return mock(CategoryCreatedEventPublisher.class);
  }
}
