package org.ddd.product.infrastructure;

import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.product.infrastructure.persistence.PostgresProductRepository;
import org.ddd.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@SpringBootApplication(scanBasePackages = { "org.ddd.product", "org.ddd.shared" })
public class ProductInfrastructureTestConfiguration {

    @Bean(name = "productJpaRepositoryTest")
    public ProductJpaRepository productJpaRepositoryTest() {
        return mock(ProductJpaRepository.class);
    }

    @Bean(name = "productRepositoryInfraTest")
    public ProductRepository productRepositoryTest() {
        return new PostgresProductRepository(productJpaRepositoryTest());
    }

}
