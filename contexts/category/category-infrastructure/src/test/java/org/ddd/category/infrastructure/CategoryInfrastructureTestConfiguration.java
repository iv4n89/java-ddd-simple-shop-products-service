package org.ddd.category.infrastructure;

import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.category.infrastructure.persistence.CategoryJpaRepository;
import org.ddd.category.infrastructure.persistence.PostgresCategoryRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@SpringBootApplication(scanBasePackages = { "org.ddd.category", "org.ddd.shared" })
public class CategoryInfrastructureTestConfiguration {

    @Bean(name = "categoryJpaRepositoryTest")
    public CategoryJpaRepository categoryJpaRepositoryTest() {
        return mock(CategoryJpaRepository.class);
    }

    @Bean(name = "categoryRepositoryInfraTest")
    public CategoryRepository categoryRepositoryTest() {
        return new PostgresCategoryRepository(categoryJpaRepositoryTest());
    }

}
